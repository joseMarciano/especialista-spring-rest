package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>,
        CustomizeRestauranteRepository,
        JpaSpecificationExecutor<Restaurante> {

}