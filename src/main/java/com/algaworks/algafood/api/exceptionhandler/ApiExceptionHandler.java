package com.algaworks.algafood.api.exceptionhandler;


import com.algaworks.algafood.api.exceptionhandler.Problem.ProblemBuilder;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntidadeNaoEncontradaException.class})
    public ResponseEntity<?> handleEstadoNaoEncontradoException(EntidadeNaoEncontradaException e, WebRequest webRequest) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.ENTIDADE_NAO_ENCONTRADA;

        Problem problem =
                problemBuilder(status, type, e.getMessage())
                        .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, webRequest);
    }

    @ExceptionHandler({NegocioException.class})
    public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest webRequest) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType type = ProblemType.ERRO_NEGOCIO;

        Problem problem =
                problemBuilder(status, type, e.getMessage())
                        .build();


        return handleExceptionInternal(e, problem, new HttpHeaders(), status, webRequest);
    }

    @ExceptionHandler({EntidadeEmUsoException.class})
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest webRequest) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType type = ProblemType.ENTIDADE_EM_USO;

        Problem problem =
                problemBuilder(status, type, e.getMessage())
                        .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, webRequest);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        ProblemType type = ProblemType.MENSAGEM_INCOMPREENSIVEL;

        Problem problem =
                problemBuilder(status, type, null)
                        .detail("Corpo da requisição está inválido. Verifique erro de sintaxe")
                        .build();


        return handleExceptionInternal(e, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        if (Objects.isNull(body)) {
            body = Problem.builder()
                    .detail(ex.getMessage())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .detail(ex.getMessage())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ProblemBuilder problemBuilder(HttpStatus status, ProblemType type, String detail) {
        return Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .type(type.getUri())
                .detail(detail)
                .title(type.getTitle());
    }

}
