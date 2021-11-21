package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Cozinha find(@PathVariable Long id) {
        return cozinhaService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha save(@RequestBody @Valid Cozinha cozinha) {
        return cozinhaService.save(cozinha);
    }

    @PutMapping("{id}")
    public Cozinha update(@PathVariable Long id,
                          @RequestBody @Valid Cozinha cozinhaBody) {

        cozinhaService.buscarOuFalhar(id);

        return cozinhaService.save(cozinhaBody);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        cozinhaService.remove(id);
    }
}
