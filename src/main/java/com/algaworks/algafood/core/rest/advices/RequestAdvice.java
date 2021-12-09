package com.algaworks.algafood.core.rest.advices;


import com.algaworks.algafood.core.rest.Representation;
import com.algaworks.algafood.core.rest.RepresentationProvider;
import com.algaworks.algafood.core.rest.RequestModel;
import com.algaworks.algafood.core.rest.advices.util.IterceptorsUtil;
import com.algaworks.algafood.domain.model.Entity;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;


@RestControllerAdvice
public class RequestAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.getMethod() != null && methodParameter.getMethod().isAnnotationPresent(RequestModel.class)
                && (methodParameter.getMethod().isAnnotationPresent(PostMapping.class)
                || methodParameter.getMethod().isAnnotationPresent(PutMapping.class));
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        RepresentationProvider provider = IterceptorsUtil.getAnnotationClassInstance(RequestModel.class, parameter);

        Object newBodyInstance = IterceptorsUtil.getNewBodyInstance(body);
        Representation representation = provider.getRepresentation();


        representation.getFields().forEach(field -> {

            IterceptorsUtil.validationIfFieldExists(representation, field);

            Method setterMethod = IterceptorsUtil.getSetterMethod(field, body);
            Method getterMethod = IterceptorsUtil.getGetterMethod(field, body);

            try {
                setterMethod.invoke(newBodyInstance, getterMethod.invoke(body, null));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        representation.getAssociations().forEach((fieldName, value) -> {

            IterceptorsUtil.validationIfFieldExists(representation, fieldName);

            try {
                RepresentationProvider repProvider = value.getConstructor().newInstance(null);
                Representation representationChild = (Representation)
                        value.getDeclaredMethod("getRepresentation", null).invoke(repProvider);
                IterceptorsUtil.resolveChild(representationChild,newBodyInstance,body, fieldName);

            } catch (IllegalAccessException
                    | InvocationTargetException
                    | InstantiationException
                    | NoSuchMethodException e) {
                e.printStackTrace();
                throw new RuntimeException("Error in getRepresentation",e.getCause());
            }
        });


        return newBodyInstance;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

}

