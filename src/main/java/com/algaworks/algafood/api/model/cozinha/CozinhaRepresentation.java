package com.algaworks.algafood.api.model.cozinha;

import lombok.Getter;
import lombok.Setter;

public interface CozinhaRepresentation {

    @Getter
    @Setter
    class Completa {
        private Long id;
        private String nome;
    }
}
