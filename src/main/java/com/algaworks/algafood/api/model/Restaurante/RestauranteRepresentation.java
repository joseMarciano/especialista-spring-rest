package com.algaworks.algafood.api.model.Restaurante;

import com.algaworks.algafood.core.rest.Representation;
import com.algaworks.algafood.core.rest.RepresentationProvider;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import lombok.NoArgsConstructor;

public interface RestauranteRepresentation {

    @NoArgsConstructor
    class Completa implements RepresentationProvider {
        @Override
        public Representation getRepresentation() {
            return Representation.Builder.create(Restaurante.class)
                    .field("id")
                    .field("nome")
                    .association("cozinha", Associacao.class)
                    .field("taxaFrete")
                    .build();
        }
    }

    @NoArgsConstructor
    class Associacao implements RepresentationProvider {
        @Override
        public Representation getRepresentation() {
            return Representation.Builder.create(Cozinha.class)
                    .field("id")
                    .build();
        }
    }
}
