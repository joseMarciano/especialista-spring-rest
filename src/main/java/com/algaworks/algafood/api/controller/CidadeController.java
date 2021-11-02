package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }


    @GetMapping
    public List<Cidade> listAll() {
        return cidadeService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Cidade> find(@PathVariable Long id) {
        Cidade cidade = cidadeService.find(id);
        return cidade == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(cidade);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Cidade cidade) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeService.save(cidade));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody Cidade cidadeBody) {
        if (cidadeService.find(id) == null) return ResponseEntity.notFound().build();

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeService.save(cidadeBody));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        try {
            cidadeService.remove(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
