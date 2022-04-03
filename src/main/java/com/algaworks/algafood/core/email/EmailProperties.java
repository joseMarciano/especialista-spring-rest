package com.algaworks.algafood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    private String from;
    private Mail mail = new Mail();


    @Getter
    @Setter
    public class Mail {
        private String host;
        private Integer port;
        private String username;
        private String password;
    }

}
