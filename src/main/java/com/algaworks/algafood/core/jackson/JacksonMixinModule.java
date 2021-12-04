package com.algaworks.algafood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {


    public JacksonMixinModule() {
        for (MixinsMapped mixin : MixinsMapped.values()) {
            setMixInAnnotation(mixin.getTarget(), mixin.getMixinClass());
        }
    }
}
