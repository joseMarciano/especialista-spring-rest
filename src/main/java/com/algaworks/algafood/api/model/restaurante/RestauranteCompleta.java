package com.algaworks.algafood.api.model.restaurante;

import com.algaworks.algafood.domain.model.Cozinha;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class RestauranteCompleta {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private Cozinha cozinha;

    private OffsetDateTime dataAtualizacao;
}
