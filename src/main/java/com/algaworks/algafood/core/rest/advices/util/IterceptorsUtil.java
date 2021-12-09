package com.algaworks.algafood.core.rest.advices.util;

import com.algaworks.algafood.core.rest.Representation;
import com.algaworks.algafood.core.rest.RepresentationProvider;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public class IterceptorsUtil {

    public static RepresentationProvider getAnnotationClassInstance(Class<? extends Annotation> clazz,
                                                                    MethodParameter parameter) {
        Method method = parameter.getMethod();

        if (method == null) {
            throw new RuntimeException("Method not found");
        }

        Annotation annotation = method.getAnnotation(clazz);

        if (annotation == null) {
            throw new RuntimeException(clazz.getSimpleName() + "not found at " + method.getName());
        }

        try {
            Object representationClass = annotation
                    .annotationType()
                    .getDeclaredMethod("representation", null)
                    .invoke(annotation);

            Class<RepresentationProvider> representationMethod =
                    ((Class<RepresentationProvider>) representationClass);

            return representationMethod.getConstructor().newInstance(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static String buildPropNameByPattern(String name, String prefix) {
        String capitalized = StringUtils.capitalize(name);
        return format("%s%s", prefix, capitalized);
    }

    public static void validationIfFieldExists(Representation representation, String fieldName) {
        List<Field> fieldsDeclaredClass = Arrays.stream(representation.getClazz().getDeclaredFields()).toList();

        List<Field> fieldsRepresentation = Arrays.stream(representation.getClazz().getDeclaredFields()).toList();
        fieldsRepresentation.stream().filter(fieldRep -> fieldRep.getName().equals(fieldName)).findAny().orElseThrow(() ->
                new RuntimeException(format("Field %s is not mapped at representation %s", fieldName, representation)));

        fieldsDeclaredClass.stream().filter(fieldClass -> fieldClass.getName().equals(fieldName)).findAny()
                .orElseThrow(() ->
                        new RuntimeException(format("Field %s is not mapped at class %s", fieldName, representation.getClazz().getTypeName())));


    }

    public static Object getNewBodyInstance(Object body) {
        try {
            return body.getClass().getConstructor().newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("Error to new Instance of Body", e.getCause());
        }
    }

    public static Method getGetterMethod(String field, Object body) {
        String methodName = buildPropNameByPattern(field, "get");
        try {
            return body.getClass().getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(
                    format("Method %s not found in class %s", methodName, body.getClass()
                            .getSimpleName()),
                    e.getCause()
            );
        }
    }

    public static Method getSetterMethod(String field, Object body) {
        String methodName = buildPropNameByPattern(field, "set");
        try {
            Class<?> typeClassParam = (Class<?>) body.getClass().getDeclaredField(field).getGenericType();
            return body.getClass().getDeclaredMethod(methodName, typeClassParam);
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            throw new RuntimeException(
                    format("Method %s not found in class %s", methodName, body.getClass()
                            .getSimpleName()),
                    e.getCause()
            );

        }
    }

    public static void resolveChild(Representation representation, Object newBodyInstance, Object body, String fieldName) {
        representation.getFields().forEach(field ->
                IterceptorsUtil.validationIfFieldExists(representation, field)
        );

        representation.getAssociations().forEach((fieldNameAssociation, value) -> {

            IterceptorsUtil.validationIfFieldExists(representation, fieldNameAssociation);

            try {
                RepresentationProvider rep = value.getConstructor().newInstance(null);
                Representation representationChild = (Representation) value.getDeclaredMethod("getRepresentation", null).invoke(rep);
                resolveChild(representationChild, newBodyInstance, body, fieldNameAssociation);
            } catch (IllegalAccessException
                    | InvocationTargetException
                    | InstantiationException
                    | NoSuchMethodException e) {
                e.printStackTrace();
                throw new RuntimeException("Error in getRepresentation", e.getCause());
            }
        });


        Method setterMethod = IterceptorsUtil.getSetterMethod(fieldName, body);
        Method getterMethod = IterceptorsUtil.getGetterMethod(fieldName, body);

        try {
            setterMethod.invoke(newBodyInstance, getterMethod.invoke(body, null));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }



    }
}
