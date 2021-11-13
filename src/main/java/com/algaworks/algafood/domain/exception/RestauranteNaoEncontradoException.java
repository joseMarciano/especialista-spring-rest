package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final String DEFAULT_MESSAGE = "Restaurante não encontrado";

    public RestauranteNaoEncontradoException() {
        super(DEFAULT_MESSAGE);
    }

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(Long id) {
        this(String.format("Restaurante de código %d não encontrado", id));
    }
}
