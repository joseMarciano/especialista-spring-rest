package com.algaworks.algafood.domain.exception;

import java.util.Objects;

public class ExceptionFactory {

    public static final String[] PROJECT_EXCEPTION_PACKAGES = {
            "com.algaworks.algafood.domain.exception"
    };

    public static RuntimeException build(String simpleClassName) {
        RuntimeException exceptionInPackage;

        try {
            exceptionInPackage = getExceptionInPackage(simpleClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        return exceptionInPackage;

    }

    private static RuntimeException getExceptionInPackage(String simpleClassName) {
        RuntimeException exception = null;
        for (String projectExceptionPackage : PROJECT_EXCEPTION_PACKAGES) {
            try {
                exception = findClassByName(String.format("%s.%s", projectExceptionPackage, simpleClassName));
            } catch (ReflectiveOperationException e) {
                String message = e.getMessage();

                if (e instanceof NoSuchMethodException) {
                    message = "Class " + simpleClassName + " must have a default constructor";
                }

                if(e instanceof java.lang.ClassNotFoundException){
                    continue;
                }

                throw new IllegalArgumentException(message, e);
            }
        }

        checkIfClassIsValid(exception, simpleClassName);

        return exception;
    }

    private static RuntimeException findClassByName(String completeClassName) throws ReflectiveOperationException {
        Class<?> aClass = Class.forName(completeClassName);

        return (RuntimeException) aClass.getConstructor().newInstance();
    }

    private static void checkIfClassIsValid(RuntimeException exception, String simpleClassName) {
        if (Objects.nonNull(exception)) return;

        throw new ClassNotFoundException(String.format("Class %s not found", simpleClassName));
    }


}

class ClassNotFoundException extends RuntimeException {
    public ClassNotFoundException(String message) {
        super(message);
    }
}
