package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradoException extends EntidadeNaoEncontradaException {

    public CidadeNaoEncontradoException(String message) {
        super(message);
    }

    public CidadeNaoEncontradoException(Long id) {
        this(String.format("Cidade de código %d não encontrada", id));
    }
}
