package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.grupo.GrupoRepresentation;
import com.algaworks.algafood.api.model.usuario.UsuarioRepresentation;
import com.algaworks.algafood.core.mapper.ResponseMappedEntity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    private final GrupoRepository grupoRepository;
    private final UsuarioRepository usuarioRepository;

    public UsuarioGrupoController(GrupoRepository grupoRepository,
                                  UsuarioRepository usuarioRepository) {
        this.grupoRepository = grupoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @ResponseMappedEntity(mappedClass = GrupoRepresentation.Listagem.class)
    public Set<Grupo> findAll(@PathVariable Long usuarioId) {
        return usuarioRepository.buscarOuFalhar(usuarioId).getGrupos();
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping("{grupoId}/associar")
    public void associar(@PathVariable Long grupoId, @PathVariable Long usuarioId) {
        Usuario usuario = usuarioRepository.buscarOuFalhar(usuarioId);
        Grupo grupo = grupoRepository.buscarOuFalhar(grupoId);
        usuario.associar(grupo);

        grupoRepository.save(grupo);
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping("{grupoId}/desassociar")
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long usuarioId) {
        Usuario usuario = usuarioRepository.buscarOuFalhar(usuarioId);
        Grupo grupo = grupoRepository.buscarOuFalhar(grupoId);
        usuario.desassociar(grupo);

        grupoRepository.save(grupo);
    }


}
