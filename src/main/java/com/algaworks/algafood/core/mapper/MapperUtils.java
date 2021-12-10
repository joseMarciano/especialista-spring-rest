package com.algaworks.algafood.core.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperUtils {

    private final ModelMapper mapper;

    public MapperUtils() {
        this.mapper = new ModelMapper();
    }

    public Object resolve(Object source, Class<?> mappedAnnotation) {
        return this.mapper.map(source, mappedAnnotation);
    }
}
