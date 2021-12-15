package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.usuario.UsuarioRepresentation;
import com.algaworks.algafood.core.mapper.RequestMappedEntity;
import com.algaworks.algafood.core.mapper.ResponseMappedEntity;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @ResponseMappedEntity(mappedClass = UsuarioRepresentation.Listagem.class)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @GetMapping("{id}")
    @ResponseMappedEntity(mappedClass = UsuarioRepresentation.Listagem.class)
    public Usuario find(@PathVariable Long id) {
        return usuarioRepository.buscarOuFalhar(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{id}")
    @RequestMappedEntity(mappedClass = UsuarioRepresentation.Completa.class)
    public void update(@RequestBody Usuario usuario) {
        Boolean hasDuplicatedEmail = usuarioRepository.existsByEmail(usuario.getEmail(), usuario.getId()) > 0;

        if (hasDuplicatedEmail)
            throw new NegocioException("Já existe um usuário com o e-email informado");

        usuarioRepository.save(usuario);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{id}/alterar-senha")
    @Transactional
    public void update(@PathVariable Long id, @RequestParam String novaSenha) {
        usuarioRepository.alterarSenha(id, novaSenha);
    }

    @PostMapping
    @ResponseMappedEntity(mappedClass = UsuarioRepresentation.Listagem.class)
    @RequestMappedEntity(mappedClass = UsuarioRepresentation.Completa.class)
    public Usuario save(@RequestBody Usuario usuario) {
        Boolean hasDuplicatedEmail = usuarioRepository.existsByEmail(usuario.getEmail()) > 0;

        if (hasDuplicatedEmail)
            throw new NegocioException("Já existe um usuário com o e-email informado");

        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        try {
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Usuário de código %d não pode ser removida pois está em uso.", id));
        }

    }


}
