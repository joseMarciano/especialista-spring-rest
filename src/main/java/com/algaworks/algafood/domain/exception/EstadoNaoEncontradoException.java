package com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final String DEFAULT_MESSAGE = "Estado não encontrado";

    public EstadoNaoEncontradoException() {
        super(DEFAULT_MESSAGE);
    }

    public EstadoNaoEncontradoException(String message) {
        super(message);
    }

    public EstadoNaoEncontradoException(Long id) {
        this(String.format("Estado de código %d não encontrada", id));
    }
}
