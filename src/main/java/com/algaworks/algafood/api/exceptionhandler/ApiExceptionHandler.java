package com.algaworks.algafood.api.exceptionhandler;


import com.algaworks.algafood.api.exceptionhandler.Problema.ProblemaBuilder;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntidadeNaoEncontradaException.class})
    public ResponseEntity<?> handleEstadoNaoEncontradoException(EntidadeNaoEncontradaException e, WebRequest webRequest) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler({NegocioException.class})
    public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest webRequest) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler({EntidadeEmUsoException.class})
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest webRequest) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        if (Objects.isNull(body)) {
            body = problemadefaultBuilder()
                    .mensagem(status.getReasonPhrase())
                    .build();
        } else if (body instanceof String) {
            body = problemadefaultBuilder()
                    .mensagem((String) body)
                    .build();
        }


        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ProblemaBuilder problemadefaultBuilder() {
        return Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem("Ocorreu um erro interno no servidor");
    }

}
