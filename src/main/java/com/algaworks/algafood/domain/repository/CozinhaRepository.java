package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Cozinha> consultarPorNome(String nome, @Param("id") Long cozinha);

}