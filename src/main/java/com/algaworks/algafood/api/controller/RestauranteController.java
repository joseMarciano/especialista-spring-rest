package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.restaurante.RestauranteRepresentation;
import com.algaworks.algafood.core.mapper.RequestMappedEntity;
import com.algaworks.algafood.core.mapper.ResponseMappedEntity;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
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
