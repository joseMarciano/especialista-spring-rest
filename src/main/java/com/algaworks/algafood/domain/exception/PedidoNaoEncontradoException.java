package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final String DEFAULT_MESSAGE = "Pedido não encontrada";

    public PedidoNaoEncontradoException() {
        super(DEFAULT_MESSAGE);
    }

    public PedidoNaoEncontradoException(String message) {
        super(message);
    }

    public PedidoNaoEncontradoException(Long id) {
        this(String.format("Pedido de código %d não encontrada", id));
    }
}
