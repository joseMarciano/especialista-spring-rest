package com.algaworks.algafood.config;

import com.algaworks.algafood.core.email.EmailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JavaMailSenderConfig {

    private final EmailProperties emailProperties;

    public JavaMailSenderConfig(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(this.emailProperties.getMail().getHost());
        javaMailSender.setUsername(this.emailProperties.getMail().getUsername());
        javaMailSender.setPassword(this.emailProperties.getMail().getPassword());
        javaMailSender.setPort(this.emailProperties.getMail().getPort());
        return javaMailSender;
    }
}
