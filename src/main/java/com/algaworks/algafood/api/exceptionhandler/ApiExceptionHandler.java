package com.algaworks.algafood.api.exceptionhandler;


import com.algaworks.algafood.api.exceptionhandler.Problem.ProblemBuilder;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handlException(Exception e, WebRequest webRequest) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType type = ProblemType.ERRO_DE_SISTEMA;

        Problem problem =
                problemBuilder(status, type, "Ocorreu um erro interno inesperado no sistema")
                        .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, webRequest);
    }

    @ExceptionHandler({EntidadeNaoEncontradaException.class})
    public ResponseEntity<?> handleEstadoNaoEncontradoException(EntidadeNaoEncontradaException e, WebRequest webRequest) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.ENTIDADE_NAO_ENCONTRADA;

        Problem problem =
                problemBuilder(status, type, e.getMessage())

                        .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, webRequest);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, WebRequest webRequest) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType type = ProblemType.PARAMETRO_INVALIDO;

        String detail =
                String.format("O parâmero de URL '%s' recebeu o valor '%s', " +
                                "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'",
                        e.getParameter().getParameterName(), e.getValue().toString(), e.getRequiredType().getSimpleName());

        Problem problem =
                problemBuilder(status, type, detail)
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
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType type = ProblemType.DADOS_INVALIDOS;


        BindingResult bindingResult = e.getBindingResult();

        List<Problem.Field> messagesErrorsFields =
                bindingResult.getFieldErrors().stream().map(fieldError -> {
                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                    return Problem.Field.builder()
                            .name(fieldError.getField())
                            .userMessage(message)
                            .build();
                }).collect(Collectors.toList());

        Problem problem =
                problemBuilder(status, type, e.getMessage())
                        .userMessage("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente")
                        .fields(messagesErrorsFields)
                        .build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.",
                ex.getRequestURL());

        Problem problem = problemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(e);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }

        if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingExceptionException((PropertyBindingException) rootCause, headers, status, request);
        }


        ProblemType type = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        Problem problem =
                problemBuilder(status, type, null)
                        .detail("Corpo da requisição está inválido. Verifique erro de sintaxe")
                        .build();


        return handleExceptionInternal(e, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingExceptionException(PropertyBindingException rootCause,
                                                                           HttpHeaders headers,
                                                                           HttpStatus status,
                                                                           WebRequest request) {


        ProblemType type = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        Problem problem =
                problemBuilder(status, type, null)
                        .detail(String.format("A propriedade '%s.%s' é inválida",
                                rootCause.getReferringClass().getSimpleName(), rootCause.getPropertyName()))
                        .build();


        return handleExceptionInternal(rootCause, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException e,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {

        String fieldValue = e.getValue().toString();
        String fieldTargetType = e.getTargetType().getSimpleName();
        String fieldName = concatPath(e.getPath());

        ProblemType type = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        Problem problem =
                problemBuilder(status, type, null)
                        .detail(String.format("A propriedade '%s' recebeu um valor '%s', " +
                                        "que é de um tipo inválido. Corriga e informe um valor compatível " +
                                        "com o tipo %s."
                                , fieldName, fieldValue, fieldTargetType))
                        .build();


        return handleExceptionInternal(e, problem, headers, status, request);
    }

    private String concatPath(List<Reference> referenceList) {
        return referenceList.stream().map(Reference::getFieldName).collect(Collectors.joining("."));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        if (Objects.isNull(body)) {
            body = Problem.builder()
                    .status(status.value())
                    .detail(status.getReasonPhrase())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .status(status.value())
                    .detail(ex.getMessage())
                    .build();
        }

        ex.printStackTrace();
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ProblemBuilder problemBuilder(HttpStatus status, ProblemType type, String detail) {
        return Problem.builder()
                .timestamp(LocalDateTime.now())
                .userMessage("Ocorreu um erro no servidor")
                .status(status.value())
                .type(type.getUri())
                .detail(detail)
                .title(type.getTitle());
    }

}
