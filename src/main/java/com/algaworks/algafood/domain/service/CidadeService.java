package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        return cidadeRepository.listar();
    }

    public Cidade find(Long id) {
        return cidadeRepository.buscar(id);
    }

    public Cidade save(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();

        if(Objects.isNull(estadoRepository.buscar(estadoId)))
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de estado com código %d", estadoId)
            );

        return cidadeRepository.salvar(cidade);
    }

    public ResponseEntity<Cidade> update(Long id,
                                         Cidade cidadeBody) {
        if (cidadeRepository.buscar(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cidadeRepository.salvar(cidadeBody));
    }

    public void remove(Long id) {
        try {
            cidadeRepository.remover(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Cidade de código %d não encontrada", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removida pois está em uso.", id));
        }
    }
}
