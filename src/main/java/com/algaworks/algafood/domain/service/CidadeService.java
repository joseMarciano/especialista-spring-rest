package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;

    public CidadeService(CidadeRepository cidadeRepository,
                         EstadoRepository estadoRepository) {
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
    }

    public List<Cidade> listAll() {
        return cidadeRepository.findAll();
    }

    public Cidade find(Long id) {
        return cidadeRepository.findById(id).orElse(null);
    }

    public Cidade buscarOuFalhar(Long id) {
        return cidadeRepository.buscarOuFalhar(id);
    }

    public Cidade save(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();

        estadoRepository.buscarOuFalhar(estadoId);

        return cidadeRepository.save(cidade);
    }

    public ResponseEntity<Cidade> update(Long id,
                                         Cidade cidadeBody) {
        if (find(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cidadeRepository.save(cidadeBody));
    }

    public void remove(Long id) {
        try {
            cidadeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Cidade de código %d não encontrada", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removida pois está em uso.", id));
        }
    }
}
