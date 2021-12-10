package com.algaworks.algafood.core.context;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ContextProvider {

    private final ApplicationContext applicationContext;
    private static ApplicationContext applicationStaticContext;

    public ContextProvider(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    private void loadStaticApplicationContextBean() {
        applicationStaticContext = this.applicationContext;
    }


    public static <T> T getBean(Class<T> clazz) {
        return applicationStaticContext.getBean(clazz);
    }

}
