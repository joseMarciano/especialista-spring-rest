package com.algaworks.algafood.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
public class RestauranteDto {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaDto cozinha;
}
