package com.algaworks.algafood.api.model.restaurante;

import com.algaworks.algafood.domain.model.Cozinha;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public interface RestauranteRepresentation {

    @Getter
    @Setter
    class RestauranteCompleta {
        private Long id;
        private String nome;
        private BigDecimal taxaFrete;
        private Boolean ativo;
        private Cozinha cozinha;
    }

    @Getter
    @Setter
    class RestauranteListagem {
        private Long id;
        private String nome;
        private Boolean ativo;
    }


}
