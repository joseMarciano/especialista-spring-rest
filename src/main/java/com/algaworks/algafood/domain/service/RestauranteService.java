package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public List<Restaurante> listAll() {
        return restauranteRepository.listar();
    }

    public Restaurante find(@PathVariable Long id) {
        return restauranteRepository.buscar(id);
    }

    public Restaurante save(@RequestBody Restaurante restaurante) {
        return restauranteRepository.salvar(restaurante);
    }

    public ResponseEntity<Restaurante> update(@PathVariable Long id,
                                          @RequestBody Restaurante restauranteBody) {
        if (restauranteRepository.buscar(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(restauranteRepository.salvar(restauranteBody));
    }

    public void remove(Long id) {
        try {
            restauranteRepository.remover(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Restaurante de código %d não encontrada.", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Resturante de código %d não pode ser removido pois está em uso.", id));
        }
    }
}
