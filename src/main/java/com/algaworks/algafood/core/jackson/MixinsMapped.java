package com.algaworks.algafood.core.jackson;

import com.algaworks.algafood.api.model.mixin.CidadeMixin;
import com.algaworks.algafood.api.model.mixin.CozinhaMixin;
import com.algaworks.algafood.api.model.mixin.RestauranteMixin;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

public enum MixinsMapped {

    RESTAURANTE_MIXIN(Restaurante.class, RestauranteMixin.class),
    CIDADE_MIXIN(Cidade.class, CidadeMixin.class),
    COZINHA_MIXIN(Cozinha.class, CozinhaMixin.class);

    private Class<?> target;
    private Class<?> mixinClass;

    MixinsMapped(Class<?> target, Class<?> mixinClass) {
        this.target = target;
        this.mixinClass = mixinClass;
    }

    public Class<?> getTarget() {
        return target;
    }

    public Class<?> getMixinClass() {
        return mixinClass;
    }
}
