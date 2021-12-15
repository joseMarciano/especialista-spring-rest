package com.algaworks.algafood.api.model.produto;

import com.algaworks.algafood.api.model.restaurante.RestauranteRepresentation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public interface ProdutoRepresentation {

    @Getter
    @Setter
    class RestauranteAssociacao {
        private Long id;
        private String nome;
        private String descricao;
        private BigDecimal preco;
        private boolean ativo;
    }

    @Getter
    @Setter
    class Listagem {
        private Long id;
        private String nome;
        private String descricao;
        private BigDecimal preco;
        private boolean ativo;
    }

    @Getter
    @Setter
    class Completa {
        private Long id;
        private String nome;
        private String descricao;
        private BigDecimal preco;
        private boolean ativo;
        private RestauranteRepresentation.Associacao restaurante;
    }
}
