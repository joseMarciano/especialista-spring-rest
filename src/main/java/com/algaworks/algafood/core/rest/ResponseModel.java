package com.algaworks.algafood.core.rest;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ResponseModel {

    Class<? extends RepresentationProvider> representation();


}
