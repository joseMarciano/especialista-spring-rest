package com.algaworks.algafood.api.model.pedido;

import com.algaworks.algafood.api.model.endereco.EnderecoRepresentation;
import com.algaworks.algafood.api.model.formapagamento.FormaPagamentoRepresentation;
import com.algaworks.algafood.api.model.itempedido.ItemPedidoRepresentation;
import com.algaworks.algafood.api.model.restaurante.RestauranteRepresentation;
import com.algaworks.algafood.api.model.usuario.UsuarioRepresentation;
import com.algaworks.algafood.domain.model.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public interface PedidoRepresentation {
    @Getter
    @Setter
    class Listagem {
        private Long id;
        private BigDecimal subTotal;
        private BigDecimal taxaFrete;
        private BigDecimal valorTotal;
        private OffsetDateTime dataCriacao;
        private OffsetDateTime dataConfirmacao;
        private OffsetDateTime dataCancelamento;
        private OffsetDateTime dataEntrega;
        private StatusPedido statusPedido;
        private UsuarioRepresentation.AssociacaoPedido cliente;
        private RestauranteRepresentation.AssociacaoPedido restaurante;
        private FormaPagamentoRepresentation.AssociacaoPedido formaPagamento;
        private List<ItemPedidoRepresentation.AssociacaoPedido> itensPedido = new ArrayList<>();
        private EnderecoRepresentation.AssociacaoPedido endereco;
    }
}
