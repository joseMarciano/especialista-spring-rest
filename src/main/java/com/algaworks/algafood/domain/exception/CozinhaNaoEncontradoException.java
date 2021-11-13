package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final String DEFAULT_MESSAGE = "Cozinha não encontrada";

    public CozinhaNaoEncontradoException() {
        super(DEFAULT_MESSAGE);
    }

    public CozinhaNaoEncontradoException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradoException(Long id) {
        this(String.format("Cozinha de código %d não encontrada", id));
    }
}
