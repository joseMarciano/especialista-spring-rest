package com.algaworks.algafood.core.mapper;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface ResponseMappedEntity {

    Class<?> mappedClass() default Class.class;

}
