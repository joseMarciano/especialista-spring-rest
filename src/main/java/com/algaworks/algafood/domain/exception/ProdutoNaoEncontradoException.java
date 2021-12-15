package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final String DEFAULT_MESSAGE = "Produto não encontrado";

    public ProdutoNaoEncontradoException() {
        super(DEFAULT_MESSAGE);
    }

    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public ProdutoNaoEncontradoException(Long id) {
        this(String.format("Produto de código %d não encontrado", id));
    }
}
