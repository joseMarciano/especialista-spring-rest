package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.formapagamento.FormaPagamentoRepresentation;
import com.algaworks.algafood.core.mapper.ResponseMappedEntity;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    private final RestauranteService restauranteService;

    public RestauranteFormaPagamentoController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @GetMapping
    @ResponseMappedEntity(mappedClass = FormaPagamentoRepresentation.Listagem.class)
    public Set<FormaPagamento> findAll(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        return restaurante.getFormasPagamento();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long id) {
        restauranteService.desassociarFormaPagamento(restauranteId, id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long id) {
        restauranteService.associarFormaPagamento(restauranteId, id);
    }
}
