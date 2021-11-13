package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.http.HttpStatus;
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
    public Cidade find(@PathVariable Long id) {
        return cidadeService.buscarOuFalhar(id);
    }

    @PostMapping
    public Cidade save(@RequestBody Cidade cidade) {
        try {
            return cidadeService.save(cidade);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public Cidade update(@PathVariable Long id,
                         @RequestBody Cidade cidadeBody) {

        cidadeService.buscarOuFalhar(id);
        try {
            return cidadeService.save(cidadeBody);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        cidadeService.remove(id);
    }
}
