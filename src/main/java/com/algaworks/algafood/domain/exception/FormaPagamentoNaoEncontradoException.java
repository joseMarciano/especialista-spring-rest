package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final String DEFAULT_MESSAGE = "Forma de pagamento não encontrada";

    public FormaPagamentoNaoEncontradoException() {
        super(DEFAULT_MESSAGE);
    }

    public FormaPagamentoNaoEncontradoException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradoException(Long id) {
        this(String.format("Forma de pagamento de código %d não encontrada", id));
    }
}
