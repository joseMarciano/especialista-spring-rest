package com.algaworks.algafood.core.rest;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RequestModel {

    Class<? extends RepresentationProvider> representation() default RepresentationProvider.class;


}
