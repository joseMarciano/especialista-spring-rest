package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CozinhaService {

    private final CozinhaRepository cozinhaRepository;

    public CozinhaService(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    public List<Cozinha> listAll() {
        return cozinhaRepository.listar();
    }

    public Cozinha find(@PathVariable Long id) {
        return cozinhaRepository.buscar(id);
    }

    public Cozinha save(@RequestBody Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }

    public ResponseEntity<Cozinha> update(@PathVariable Long id,
                                          @RequestBody Cozinha cozinhaBody) {
        if (cozinhaRepository.buscar(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cozinhaRepository.salvar(cozinhaBody));
    }

    public void remove(Long id) {
        try {
            cozinhaRepository.remover(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Cozinha de código %d não encontrada", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida pois está em uso.", id));
        }
    }
}
