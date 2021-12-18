package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.*;
import org.springframework.stereotype.Service;

import static com.algaworks.algafood.domain.model.StatusPedido.CRIADO;
import static java.lang.String.format;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         RestauranteRepository restauranteRepository,
                         ProdutoRepository produtoRepository,
                         UsuarioRepository usuarioRepository,
                         FormaPagamentoRepository formaPagamentoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.restauranteRepository = restauranteRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    public Pedido emitir(Pedido pedido) {
        pedido = validaPedido(pedido);

        pedido.calcularTotal();


        return pedidoRepository.save(pedido);
    }


    private Pedido validaPedido(Pedido pedido) {
        Restaurante restaurante = restauranteRepository.buscarOuFalhar(pedido.getRestaurante().getId());
        Usuario usuario = usuarioRepository.buscarOuFalhar(pedido.getCliente().getId());
        FormaPagamento formaPagamento = formaPagamentoRepository.buscarOuFalhar(pedido.getFormaPagamento().getId());
        pedido.getItensPedido().forEach(item -> {
            Produto produto = produtoRepository.buscarOuFalhar(item.getProduto().getId());
            if (!restaurante.getProdutos().contains(produto))
                throw new NegocioException("O restaurante não aceita o produto " + produto.getDescricao());

            item.setProduto(produto);
            item.setPedido(pedido);
            item.setPrecoUnitario(produto.getPreco());
        });

        pedido.setRestaurante(restaurante);
        pedido.setCliente(usuario);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setTaxaFrete(restaurante.getTaxaFrete());
        pedido.tramitarStatus(CRIADO);

        if (!restaurante.getFormasPagamento().contains(formaPagamento))
            throw new NegocioException("Forma de pagamento não é aceita pelo restaurante");

        return pedido;
    }


    public void tramitar(Pedido pedido, StatusPedido statusPedido) {
        StatusPedido statusAtual = pedido.getStatusPedido();
        boolean tramitadoComSucesso = pedido.tramitarStatus(statusPedido);

        if(!tramitadoComSucesso){
            throw new NegocioException(format("Não é possível tramitar de %s para %s",statusAtual,statusPedido));
        }

        pedidoRepository.save(pedido);
    }
}
