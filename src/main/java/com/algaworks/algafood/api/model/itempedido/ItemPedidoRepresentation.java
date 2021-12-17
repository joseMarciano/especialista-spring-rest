package com.algaworks.algafood.api.model.itempedido;

import com.algaworks.algafood.api.model.produto.ProdutoRepresentation;
import lombok.Getter;
import lombok.Setter;

public interface ItemPedidoRepresentation {

    @Getter
    @Setter
    class AssociacaoPedido {
        private Long id;
        private ProdutoRepresentation.AssociacaoItemPedido produto;
    }
}
