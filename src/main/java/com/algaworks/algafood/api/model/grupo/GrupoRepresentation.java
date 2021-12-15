package com.algaworks.algafood.api.model.grupo;

import lombok.Getter;
import lombok.Setter;

public interface GrupoRepresentation {

    @Getter
    @Setter
    class Listagem {
        private Long id;
        private String nome;
    }

    @Getter
    @Setter
    class Completa {
        private Long id;
        private String nome;
    }
}
