package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final String DEFAULT_MESSAGE = "Usuário não encontrada";

    public UsuarioNaoEncontradoException() {
        super(DEFAULT_MESSAGE);
    }

    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(Long id) {
        this(String.format("Usuário de código %d não encontrada", id));
    }
}
