package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final String DEFAULT_MESSAGE = "Cidade não encontrada";

    public CidadeNaoEncontradoException() {
        super(DEFAULT_MESSAGE);
    }

    public CidadeNaoEncontradoException(String message) {
        super(message);
    }

    public CidadeNaoEncontradoException(Long id) {
        this(String.format("Cidade de código %d não encontrada", id));
    }
}
