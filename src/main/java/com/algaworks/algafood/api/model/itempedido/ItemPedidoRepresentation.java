package com.algaworks.algafood.api.model.itempedido;

import com.algaworks.algafood.api.model.produto.ProdutoRepresentation;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;

import java.math.BigDecimal;

public interface ItemPedidoRepresentation {

    class AssociacaoPedido {
        private Long id;
        private ProdutoRepresentation.AssociacaoItemPedido produto;
    }
}
