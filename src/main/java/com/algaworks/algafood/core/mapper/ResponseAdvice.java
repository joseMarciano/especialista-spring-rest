package com.algaworks.algafood.core.mapper;


import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    private final MapperUtils mapperUtils;

    public ResponseAdvice(MapperUtils mapperUtils) {
        this.mapperUtils = mapperUtils;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = returnType.getMethod();

        if (method == null) return false;

        return method.isAnnotationPresent(ResponseMappedEntity.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ResponseMappedEntity annotation = returnType.getMethod().getAnnotation(ResponseMappedEntity.class);
        Class<?> targetClass = annotation.mappedClass();
        Object bodyMapped;
        if (body instanceof Collection<?> collectionBody) {

            bodyMapped = collectionBody.stream()
                    .map(valueBody -> mapperUtils.resolve(valueBody, targetClass))
                    .collect(Collectors.toList());
        } else {

            bodyMapped = mapperUtils.resolve(body, targetClass);
        }

        return bodyMapped;
    }

}
