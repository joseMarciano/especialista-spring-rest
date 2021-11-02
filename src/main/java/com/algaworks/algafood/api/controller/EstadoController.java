package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    private final EstadoRepository estadoRepository;
    private final EstadoService estadoService;

    public EstadoController(EstadoRepository estadoRepository, EstadoService estadoService) {
        this.estadoRepository = estadoRepository;
        this.estadoService = estadoService;
    }

    @GetMapping
    public List<Estado> listAll() {
        return estadoService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Estado> find(@PathVariable Long id) {
        Estado estado = estadoService.find(id);
        return estado == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado save(@RequestBody Estado estado) {
        return estadoService.save(estado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Estado> update(@PathVariable Long id,
                                         @RequestBody Estado estadoBody) {
        if (estadoService.find(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estadoService.save(estadoBody));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        try {
            estadoService.remove(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
