package com.algaworks.algafood.api.model.usuario;

import lombok.Getter;
import lombok.Setter;

public interface UsuarioRepresentation {

    @Getter
    @Setter
    class Listagem {
        private Long id;
        private String nome;
        private String email;
    }

    @Getter
    @Setter
    class Completa {
        private Long id;
        private String nome;
        private String email;
        private String senha;
    }

    @Getter
    @Setter
    public class AssociacaoPedido {
        private Long id;
        private String nome;
    }
}
