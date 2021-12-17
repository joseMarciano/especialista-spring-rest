package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.permissao.PermissaoRepresentation;
import com.algaworks.algafood.core.mapper.ResponseMappedEntity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoControllerController {

    private final GrupoRepository grupoRepository;
    private final PermissaoRepository permissaoRepository;

    public GrupoPermissaoControllerController(GrupoRepository grupoRepository,
                                              PermissaoRepository permissaoRepository) {
        this.grupoRepository = grupoRepository;
        this.permissaoRepository = permissaoRepository;
    }

    @GetMapping
    @ResponseMappedEntity(mappedClass = PermissaoRepresentation.Listagem.class)
    public Set<Permissao> findAll(@PathVariable Long grupoId) {
        return grupoRepository.buscarOuFalhar(grupoId).getPermissoes();
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping("{permissaoId}/associar")
    @ResponseMappedEntity(mappedClass = PermissaoRepresentation.Listagem.class)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        Grupo grupo = grupoRepository.buscarOuFalhar(grupoId);
        Permissao permissao = permissaoRepository.buscarOuFalhar(permissaoId);
        grupo.associar(permissao);

        grupoRepository.save(grupo);
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping("{permissaoId}/desassociar")
    @ResponseMappedEntity(mappedClass = PermissaoRepresentation.Listagem.class)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        Grupo grupo = grupoRepository.buscarOuFalhar(grupoId);
        Permissao permissao = permissaoRepository.buscarOuFalhar(permissaoId);
        grupo.desassociar(permissao);

        grupoRepository.save(grupo);
    }
}
