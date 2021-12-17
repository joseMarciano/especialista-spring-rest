package com.algaworks.algafood.api.model.permissao;

import lombok.Getter;
import lombok.Setter;

public interface PermissaoRepresentation {

    @Getter
    @Setter
    class Listagem {
        private Long id;
        private String nome;
        private String descricao;
    }

}
