package com.algaworks.algafood;

import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// Altera a classe padrão de implementação das querys... antes era SimpleJpaRepository.
// OBS: a classe informada agora extende o SimpleJpaRepository então não muda muito alem de
// criarmos mais métodos padrão para todos os repositorios
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlgafoodApiApplication.class, args);
    }

}
