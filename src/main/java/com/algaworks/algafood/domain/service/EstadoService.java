package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<Estado> listAll() {
        return estadoRepository.findAll();
    }

    public Estado find(Long id) {
        return estadoRepository.findById(id).orElse(null);
    }

    public Estado buscarOuFalhar(Long id) {
        return estadoRepository.buscarOuFalhar(id);
    }

    @Transactional
    public Estado save(Estado estado) {
        return estadoRepository.save(estado);
    }

    @Transactional
    public ResponseEntity<Estado> update(Long id,
                                         Estado estadoBody) {
        if (find(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estadoRepository.save(estadoBody));
    }

    @Transactional
    public void remove(Long id) {
        try {
            estadoRepository.deleteById(id);
            estadoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Estado de código %d não pode ser removida pois está em uso.", id));
        }
    }
}
