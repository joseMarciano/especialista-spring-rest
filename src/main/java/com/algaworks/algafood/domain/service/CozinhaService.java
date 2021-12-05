package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CozinhaService {

    private final CozinhaRepository cozinhaRepository;

    public CozinhaService(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    public List<Cozinha> listAll() {
        return cozinhaRepository.findAll();
    }

    public Cozinha find(Long id) {
        return cozinhaRepository.findById(id).orElse(null);
    }

    public Cozinha buscarOuFalhar(Long id) {
        return cozinhaRepository.buscarOuFalhar(id);
    }

    @Transactional
    public Cozinha save(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public ResponseEntity<Cozinha> update(Long id,
                                          Cozinha cozinhaBody) {
        if (find(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cozinhaRepository.save(cozinhaBody));
    }

    @Transactional
    public void remove(Long id) {
        try {
            cozinhaRepository.deleteById(id);
            cozinhaRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida pois está em uso.", id));
        }
    }
}
