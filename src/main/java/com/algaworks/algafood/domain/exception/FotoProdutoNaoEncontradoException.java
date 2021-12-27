package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final String DEFAULT_MESSAGE = "FotoProduto não encontrada";

    public FotoProdutoNaoEncontradoException() {
        super(DEFAULT_MESSAGE);
    }

    public FotoProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public FotoProdutoNaoEncontradoException(Long id) {
        this(String.format("FotoProduto de código %d não encontrada", id));
    }
}
