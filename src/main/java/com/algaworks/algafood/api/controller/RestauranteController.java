package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }


    @GetMapping
    public List<Restaurante> listAll() {
        return restauranteService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Restaurante> find(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.find(id);
        return restaurante == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(restaurante);
    }

    @PostMapping
    public Restaurante save(@RequestBody Restaurante restaurante) {
        return restauranteService.save(restaurante);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody Restaurante restauranteBody) {
        if (restauranteService.find(id) == null) return ResponseEntity.notFound().build();
        try {
            restauranteService.save(restauranteBody);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        try {
            restauranteService.remove(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
