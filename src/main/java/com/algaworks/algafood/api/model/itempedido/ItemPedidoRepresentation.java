package com.algaworks.algafood.api.model.itempedido;

import com.algaworks.algafood.api.model.produto.ProdutoRepresentation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public interface ItemPedidoRepresentation {

    @Getter
    @Setter
    class AssociacaoPedido {
        private Long id;
        private ProdutoRepresentation.AssociacaoItemPedido produto;
    }

    @Getter
    @Setter
    class Completa {
        private Long id;
        private Integer quantidade;
        private String observacao;
        private ProdutoRepresentation.Associacao produto;
    }
}
