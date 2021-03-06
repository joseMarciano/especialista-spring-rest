package com.algaworks.algafood;

import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Time;
import java.util.TimeZone;

@SpringBootApplication
// Altera a classe padrão de implementação das querys... antes era SimpleJpaRepository.
// OBS: a classe informada agora extende o SimpleJpaRepository então não muda muito alem de
// criarmos mais métodos padrão para todos os repositorios
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodApiApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")); // retorna sempre em padrão UTC (com offset 0)... fica cargo do consumidor converter
        SpringApplication.run(AlgafoodApiApplication.class, args);
    }

}
