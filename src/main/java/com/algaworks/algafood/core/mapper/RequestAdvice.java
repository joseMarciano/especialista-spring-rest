package com.algaworks.algafood.core.mapper;


import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RequestAdvice implements RequestBodyAdvice {

    private final MapperUtils mapperUtils;

    public RequestAdvice(MapperUtils mapperUtils) {
        this.mapperUtils = mapperUtils;
    }

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {

        Method method = methodParameter.getMethod();

        if (method == null) return false;


        return method.isAnnotationPresent(RequestMappedEntity.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage,
                                           MethodParameter parameter,
                                           Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body,
                                HttpInputMessage inputMessage,
                                MethodParameter parameter,
                                Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        RequestMappedEntity annotation = parameter.getMethod().getAnnotation(RequestMappedEntity.class);

        Class<?> targetClass = annotation.mappedClass();

        Object bodyMapped;
        if (body instanceof Collection<?> collectionBody) {
            Class<?> classOriginalBodyContent = ((ArrayList<?>) collectionBody).get(0).getClass();

            bodyMapped = collectionBody.stream()
                    .map(valueBody -> mapperUtils.resolve(valueBody, targetClass))
                    .collect(Collectors.toList());

            bodyMapped = ((Collection<?>) bodyMapped).stream()
                    .map(valueBody -> mapperUtils.resolve(valueBody, classOriginalBodyContent))
                    .collect(Collectors.toList());
        } else {
            bodyMapped = mapperUtils.resolve(body, targetClass);
            bodyMapped = mapperUtils.resolve(bodyMapped, body.getClass());
        }

        return bodyMapped;
    }

    @Override
    public Object handleEmptyBody(Object body,
                                  HttpInputMessage inputMessage,
                                  MethodParameter parameter,
                                  Type targetType,
                                  Class<? extends HttpMessageConverter<?>> converterType) {
        return null;
    }
}
