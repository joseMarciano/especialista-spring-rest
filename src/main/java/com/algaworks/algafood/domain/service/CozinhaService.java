package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Cozinha> listAll(Pageable pageable) {
        return cozinhaRepository.findAll(pageable);
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
            throw new EntidadeEmUsoException(String.format("Cozinha de c??digo %d n??o pode ser removida pois est?? em uso.", id));
        }
    }
}
