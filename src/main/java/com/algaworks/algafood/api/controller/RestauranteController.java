package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.restaurante.RestauranteRepresentation;
import com.algaworks.algafood.api.model.usuario.UsuarioRepresentation;
import com.algaworks.algafood.core.mapper.RequestMappedEntity;
import com.algaworks.algafood.core.mapper.ResponseMappedEntity;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;
    private final UsuarioRepository usuarioRepository;

    public RestauranteController(RestauranteService restauranteService,
                                 UsuarioRepository usuarioRepository) {
        this.restauranteService = restauranteService;
        this.usuarioRepository = usuarioRepository;
    }


    @GetMapping
    @ResponseMappedEntity(mappedClass = RestauranteRepresentation.RestauranteListagem.class)
    public List<Restaurante> listAll() {
        return restauranteService.listAll();
    }

    @GetMapping("{id}")
    @ResponseMappedEntity(mappedClass = RestauranteRepresentation.RestauranteCompleta.class)
    public Restaurante find(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(id);
        return restaurante;
    }

    @PostMapping
    @RequestMappedEntity(mappedClass = RestauranteRepresentation.RestauranteCompleta.class)
    @ResponseMappedEntity(mappedClass = RestauranteRepresentation.RestauranteCompleta.class)
    public Restaurante save(@RequestBody @Valid Restaurante restaurante) {
        try {
            return restauranteService.save(restaurante);
        } catch (CozinhaNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("{id}")
    @RequestMappedEntity(mappedClass = RestauranteRepresentation.RestauranteCompleta.class)
    @ResponseMappedEntity(mappedClass = RestauranteRepresentation.RestauranteCompleta.class)
    public Restaurante update(@PathVariable Long id,
                              @RequestBody Restaurante restauranteBody) {
        restauranteService.buscarOuFalhar(id);
        try {
            return restauranteService.save(restauranteBody);
        } catch (CozinhaNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Transactional
    @PutMapping("{restauranteId}/ativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
    }


    @GetMapping("{restauranteId}/responsaveis")
    @ResponseMappedEntity(mappedClass = UsuarioRepresentation.Listagem.class)
    public Set<Usuario> findResponsaveis(@PathVariable Long restauranteId) {
        return restauranteService.buscarOuFalhar(restauranteId).getUsuarios();
    }


    @PutMapping("{restauranteId}/associar-responsavel/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atribuirResponsaveis(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        Usuario usuario = usuarioRepository.buscarOuFalhar(responsavelId);

        restaurante.atribuirResponsavel(usuario);

        restauranteService.save(restaurante);

    }

    @PutMapping("{restauranteId}/desassociar-responsavel/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarResponsaveis(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        Usuario usuario = usuarioRepository.buscarOuFalhar(responsavelId);

        restaurante.desassociarResponsavel(usuario);

        restauranteService.save(restaurante);

    }

    @PutMapping("{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
    }

    @PutMapping("{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abertura(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
    }

    @Transactional
    @PutMapping("{restauranteId}/inativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        try {
            restauranteService.remove(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
