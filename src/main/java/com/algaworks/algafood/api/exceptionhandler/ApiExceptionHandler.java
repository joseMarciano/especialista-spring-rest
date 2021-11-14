package com.algaworks.algafood.api.exceptionhandler;


import com.algaworks.algafood.api.exceptionhandler.Problema.ProblemaBuilder;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntidadeNaoEncontradaException.class})
    public ResponseEntity<?> handleEstadoNaoEncontradoException(EntidadeNaoEncontradaException e) {

        Problema problema = problemadefaultBuilder()
                .mensagem(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
    }

    @ExceptionHandler({NegocioException.class})
    public ResponseEntity<?> handleNegocioException(NegocioException e) {

        Problema problema = problemadefaultBuilder()
                .mensagem(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
    }

    @ExceptionHandler({EntidadeEmUsoException.class})
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e) {

        Problema problema = problemadefaultBuilder()
                .mensagem(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problema);
    }


    private ProblemaBuilder problemadefaultBuilder() {
        return Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem("Ocorreu um erro interno no servidor");
    }

}
