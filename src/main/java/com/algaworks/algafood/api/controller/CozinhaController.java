package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaService cozinhaService;

    public CozinhaController(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }


    @GetMapping
    public List<Cozinha> listAll() {
        return cozinhaService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Cozinha> find(@PathVariable Long id) {
        Cozinha cozinha = cozinhaService.find(id);
        return cozinha == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha save(@RequestBody Cozinha cozinha) {
        return cozinhaService.save(cozinha);
    }

    @PutMapping("{id}")
    public ResponseEntity<Cozinha> update(@PathVariable Long id,
                                          @RequestBody Cozinha cozinhaBody) {
        if (cozinhaService.find(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cozinhaService.save(cozinhaBody));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        try {
            cozinhaService.remove(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
