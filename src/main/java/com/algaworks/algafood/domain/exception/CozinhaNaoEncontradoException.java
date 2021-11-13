package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradoException extends EntidadeNaoEncontradaException {

    public CozinhaNaoEncontradoException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradoException(Long id) {
        this(String.format("Cozinha de código %d não encontrada", id));
    }
}
