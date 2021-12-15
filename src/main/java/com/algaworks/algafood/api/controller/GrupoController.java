package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.grupo.GrupoRepresentation;
import com.algaworks.algafood.core.mapper.RequestMappedEntity;
import com.algaworks.algafood.core.mapper.ResponseMappedEntity;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final GrupoRepository grupoRepository;

    public GrupoController(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    @GetMapping
    @ResponseMappedEntity(mappedClass = GrupoRepresentation.Listagem.class)
    public List<Grupo> findAll() {
        return grupoRepository.findAll();
    }

    @GetMapping("{id}")
    @ResponseMappedEntity(mappedClass = GrupoRepresentation.Listagem.class)
    public Grupo find(@PathVariable Long id) {
        return grupoRepository.buscarOuFalhar(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    @RequestMappedEntity(mappedClass = GrupoRepresentation.Completa.class)
    public void update(@RequestBody Grupo grupo) {
        grupoRepository.save(grupo);
    }

    @PostMapping
    @ResponseMappedEntity(mappedClass = GrupoRepresentation.Listagem.class)
    @RequestMappedEntity(mappedClass = GrupoRepresentation.Completa.class)
    public Grupo save(@RequestBody Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Grupo de código %d não pode ser removida pois está em uso.", id));
        }

    }


}
