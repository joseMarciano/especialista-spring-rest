package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.produto.FotoProdutoDto;
import com.algaworks.algafood.api.model.produto.ProdutoRepresentation;
import com.algaworks.algafood.core.mapper.RequestMappedEntity;
import com.algaworks.algafood.core.mapper.ResponseMappedEntity;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.Set;

@RestController
@RequestMapping("restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    private final RestauranteService restauranteService;
    private final ProdutoRepository produtoRepository;

    public RestauranteProdutoController(RestauranteService restauranteService,
                                        ProdutoRepository produtoRepository) {
        this.restauranteService = restauranteService;
        this.produtoRepository = produtoRepository;
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
            throw new ProdutoNaoEncontradoException("O produto solicitado não existe para este restaurante");
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
            throw new ProdutoNaoEncontradoException("O produto solicitado não existe para este restaurante");
        }

        produto.setRestaurante(restaurante);
        return produtoRepository.save(produto);
    }

    @PutMapping(path = "{produtoId}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,  FotoProdutoDto fotoProdutoDto) throws IOException {

        MultipartFile file = fotoProdutoDto.getFile();
        String name = file.getName();

        Path filePath = Path.of("/home/jose/JOSEPAULO/algafood-api/src/main/resources/static", name);

        file.transferTo(filePath);


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
