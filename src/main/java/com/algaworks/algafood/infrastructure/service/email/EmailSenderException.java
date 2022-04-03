package com.algaworks.algafood.infrastructure.service.email;

public class EmailSenderException extends RuntimeException {

    public EmailSenderException(String message) {
        super(message);
    }

    public EmailSenderException(String message, Throwable cause) {
        super(message, cause);
    }
}
