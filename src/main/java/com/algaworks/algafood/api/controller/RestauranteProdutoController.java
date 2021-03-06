package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.fotoproduto.FotoProdutoRepresentation;
import com.algaworks.algafood.api.model.produto.FotoProdutoDto;
import com.algaworks.algafood.api.model.produto.ProdutoRepresentation;
import com.algaworks.algafood.core.mapper.RequestMappedEntity;
import com.algaworks.algafood.core.mapper.ResponseMappedEntity;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorage;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Set;

import static com.algaworks.algafood.domain.service.FotoStorage.FotoRecuperada;
import static com.algaworks.algafood.domain.service.FotoStorage.NovaFoto;

@RestController
@RequestMapping("restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    private final RestauranteService restauranteService;
    private final ProdutoRepository produtoRepository;
    private final CatalogoFotoProdutoService catalogoFotoProdutoService;
    private final FotoStorage fotoStorageService;

    public RestauranteProdutoController(RestauranteService restauranteService,
                                        ProdutoRepository produtoRepository,
                                        CatalogoFotoProdutoService catalogoFotoProdutoService,
                                        FotoStorage fotoStorageService) {
        this.restauranteService = restauranteService;
        this.produtoRepository = produtoRepository;
        this.catalogoFotoProdutoService = catalogoFotoProdutoService;
        this.fotoStorageService = fotoStorageService;
    }

    @GetMapping
    @ResponseMappedEntity(mappedClass = ProdutoRepresentation.Listagem.class)
    public Set<Produto> findAll(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        return restaurante.getProdutos();
    }

    @GetMapping("{id}")
    @ResponseMappedEntity(mappedClass = ProdutoRepresentation.Listagem.class)
    public Produto find(@PathVariable Long restauranteId, @PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        Produto produto = produtoRepository.findByRestauranteId(id, restaurante.getId());

        if (produto == null) {
            throw new ProdutoNaoEncontradoException("O produto solicitado n??o existe para este restaurante");
        }

        return produto;
    }

    @PostMapping
    @ResponseMappedEntity(mappedClass = ProdutoRepresentation.Listagem.class)
    @RequestMappedEntity(mappedClass = ProdutoRepresentation.Completa.class)
    public Produto save(@PathVariable Long restauranteId, @RequestBody Produto produto) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        produto.setRestaurante(restaurante);

        return produtoRepository.save(produto);
    }

    @PutMapping("{id}")
    @ResponseMappedEntity(mappedClass = ProdutoRepresentation.Listagem.class)
    @RequestMappedEntity(mappedClass = ProdutoRepresentation.Completa.class)
    public Produto update(@PathVariable Long restauranteId, @RequestBody Produto produto) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        boolean existsProduto =
                produtoRepository.findByRestauranteId(produto.getId(), restaurante.getId()) != null;


        if (!existsProduto) {
            throw new ProdutoNaoEncontradoException("O produto solicitado n??o existe para este restaurante");
        }

        produto.setRestaurante(restaurante);
        return produtoRepository.save(produto);
    }

    @PutMapping(path = "{produtoId}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseMappedEntity(mappedClass = FotoProdutoRepresentation.Listagem.class)
    public FotoProduto atualizarFoto(@PathVariable Long restauranteId,
                                     @PathVariable Long produtoId,
                                     @Valid FotoProdutoDto fotoProdutoDto) throws IOException {

        Produto produto = produtoRepository.findByRestauranteId(produtoId, restauranteId);

        if (produto == null) {
            throw new ProdutoNaoEncontradoException("O produto solicitado n??o existe para este restaurante");
        }

        FotoProduto fotoProduto = produtoRepository.findFotoById(restauranteId, produtoId);
        String nomeArquivoAntigo = null;

        if (fotoProduto != null) {
            nomeArquivoAntigo = fotoProduto.getNome();
            produtoRepository.delete(fotoProduto);
        }

        FotoProduto foto = new FotoProduto();
        foto.setProdutos(produto);
        foto.setContentType(fotoProdutoDto.getFile().getContentType());
        foto.setDescricao(fotoProdutoDto.getDescricao());
        foto.setTamanho(fotoProdutoDto.getFile().getSize());
        foto.setNome(fotoStorageService.gerarNomeArquivo(fotoProdutoDto.getFile().getOriginalFilename()));

        FotoProduto fotoSaved = catalogoFotoProdutoService.save(foto);
        produtoRepository.flush();


        NovaFoto novaFoto = NovaFoto
                .builder()
                .nomeArquivo(fotoSaved.getNome())
                .file(fotoProdutoDto.getFile().getInputStream())
                .contentType(fotoSaved.getContentType())
                .build();

        fotoStorageService.substituir(nomeArquivoAntigo, novaFoto);

        return fotoSaved;

    }

    @GetMapping(value = "{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseMappedEntity(mappedClass = FotoProdutoRepresentation.Listagem.class)
    FotoProduto getFotoProduto(@PathVariable("restauranteId") Long restauranteId, @PathVariable("produtoId") Long produtoId) {
        Produto produto = produtoRepository.findByRestauranteId(produtoId, restauranteId);

        if (produto == null) {
            throw new ProdutoNaoEncontradoException("O produto solicitado n??o existe para este restaurante");
        }

        FotoProduto fotoById = produtoRepository.findFotoById(restauranteId, produtoId);

        if (fotoById == null)
            throw new FotoProdutoNaoEncontradoException(String.format("N??o existe um cadastro de foto do produto com c??digo %d para o restaurante de c??digo %d",
                    produtoId, restauranteId));


        return fotoById;

    }

    @DeleteMapping("{produtoId}/foto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId,
                        @PathVariable Long produtoId) {
        Produto produto = produtoRepository.findByRestauranteId(produtoId, restauranteId);

        if (produto == null) {
            throw new ProdutoNaoEncontradoException("O produto solicitado n??o existe para este restaurante");
        }

        FotoProduto fotoById = produtoRepository.findFotoById(restauranteId, produtoId);

        if (fotoById == null)
            throw new FotoProdutoNaoEncontradoException(String.format("N??o existe um cadastro de foto do produto com c??digo %d para o restaurante de c??digo %d",
                    produtoId, restauranteId));

        produtoRepository.delete(fotoById);
        produtoRepository.flush();
        fotoStorageService.remover(fotoById.getNome());
    }

    @GetMapping(value = "{produtoId}/foto", produces = MediaType.TEXT_PLAIN_VALUE)
    ResponseEntity<InputStreamResource> servirFoto(@PathVariable("restauranteId") Long restauranteId, @PathVariable("produtoId") Long produtoId) {
        Produto produto = produtoRepository.findByRestauranteId(produtoId, restauranteId);

        if (produto == null) {
            throw new ProdutoNaoEncontradoException("O produto solicitado n??o existe para este restaurante");
        }

        FotoProduto fotoById = produtoRepository.findFotoById(restauranteId, produtoId);

        if (fotoById == null)
            throw new FotoProdutoNaoEncontradoException(String.format("N??o existe um cadastro de foto do produto com c??digo %d para o restaurante de c??digo %d",
                    produtoId, restauranteId));

        FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoById.getNome());

        if (fotoRecuperada.getUrl() != null) {
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION,fotoRecuperada.getUrl())
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(MediaType.TEXT_PLAIN_VALUE))
                    .body(new InputStreamResource(fotoRecuperada.getInputStream()));
        }


    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> remove(@PathVariable Long restauranteId, @PathVariable Long id) {
        try {
            restauranteService.buscarOuFalhar(restauranteId);
            produtoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
