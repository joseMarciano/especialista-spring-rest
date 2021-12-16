package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final CozinhaRepository cozinhaRepository;

    public RestauranteService(RestauranteRepository restauranteRepository,
                              FormaPagamentoRepository formaPagamentoRepository, CozinhaRepository cozinhaRepository) {
        this.restauranteRepository = restauranteRepository;
        this.formaPagamentoRepository = formaPagamentoRepository;
        this.cozinhaRepository = cozinhaRepository;
    }

    public List<Restaurante> listAll() {
        return restauranteRepository.findAll();
    }

    public Restaurante find(@PathVariable Long id) {
        return restauranteRepository.findById(id).orElse(null);
    }

    @Transactional
    public Restaurante save(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        cozinhaRepository.buscarOuFalhar(cozinhaId);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public ResponseEntity<Restaurante> update(Long id,
                                              Restaurante restauranteBody) {
        if (find(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(restauranteRepository.save(restauranteBody));
    }

    @Transactional
    public void remove(Long id) {
        try {
            restauranteRepository.deleteById(id);
            restauranteRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Resturante de código %d não pode ser removido pois está em uso.", id));
        }
    }

    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository.buscarOuFalhar(id);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = restauranteRepository.buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoRepository.buscarOuFalhar(formaPagamentoId);
        restauranteRepository.desassociarFormaPagamento(restaurante.getId(), formaPagamento.getId());
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = restauranteRepository.buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoRepository.buscarOuFalhar(formaPagamentoId);
        restaurante.associarFormaPagamento(formaPagamento); //NÃO PRECISO DAR UM SAVE POIS O HIBERNATE JÁ FAZ ISSO QUANDO TERMINA O MÉTODO TRANSACIONADO
    }

    public void ativar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.ativar();
        restauranteRepository.save(restaurante);
    }

    public void inativar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.inativar();
        restauranteRepository.save(restaurante);
    }

    public void fechar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.fechar();
        restauranteRepository.save(restaurante);
    }

    public void abrir(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.abrir();
        restauranteRepository.save(restaurante);
    }
}
