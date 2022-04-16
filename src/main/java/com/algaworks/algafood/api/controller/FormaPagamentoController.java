package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.formapagamento.FormaPagamentoRepresentation;
import com.algaworks.algafood.core.mapper.RequestMappedEntity;
import com.algaworks.algafood.core.mapper.ResponseMappedEntity;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    private final FormaPagamentoService formaPagamentoService;

    public FormaPagamentoController(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    @PostMapping
    @Transactional
    @ResponseMappedEntity(mappedClass = FormaPagamentoRepresentation.Listagem.class)
    @RequestMappedEntity(mappedClass = FormaPagamentoRepresentation.Completa.class)
    public FormaPagamento save(@RequestBody @Valid FormaPagamento formaPagamento) {
        return formaPagamentoService.save(formaPagamento);
    }

    @PutMapping("{id}")
    @Transactional
    @ResponseMappedEntity(mappedClass = FormaPagamentoRepresentation.Listagem.class)
    @RequestMappedEntity(mappedClass = FormaPagamentoRepresentation.Completa.class)
    public FormaPagamento update(@RequestBody @Valid FormaPagamento formaPagamento, @PathVariable Long id) {
        formaPagamentoService.buscarOuFalhar(id);
        return formaPagamentoService.save(formaPagamento);
    }


    @GetMapping
    @Transactional
    @ResponseMappedEntity(mappedClass = FormaPagamentoRepresentation.Listagem.class)
    public ResponseEntity<List<FormaPagamento>> findAll() {
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoService.findAll());
    }


    @GetMapping("{id}")
    @Transactional
    @ResponseMappedEntity(mappedClass = FormaPagamentoRepresentation.Listagem.class)
    public FormaPagamento find(@PathVariable Long id) {
        return formaPagamentoService.buscarOuFalhar(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> remove(@PathVariable Long id) {
        try {
            formaPagamentoService.remove(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
