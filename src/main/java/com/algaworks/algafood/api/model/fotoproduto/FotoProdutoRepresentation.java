package com.algaworks.algafood.api.model.fotoproduto;

import lombok.Getter;
import lombok.Setter;

public interface FotoProdutoRepresentation {

    @Getter
    @Setter
    class Listagem {
        private String nome;
        private String descricao;
        private String contentType;
        private Integer tamanho;
    }

}
