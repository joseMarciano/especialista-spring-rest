package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final String DEFAULT_MESSAGE = "Grupo não encontrada";

    public GrupoNaoEncontradoException() {
        super(DEFAULT_MESSAGE);
    }

    public GrupoNaoEncontradoException(String message) {
        super(message);
    }

    public GrupoNaoEncontradoException(Long id) {
        this(String.format("Grupo de código %d não encontrada", id));
    }
}
