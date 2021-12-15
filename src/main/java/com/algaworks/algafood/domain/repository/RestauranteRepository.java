package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>,
        CustomizeRestauranteRepository,
        JpaSpecificationExecutor<Restaurante> {

    @Query("from Restaurante r join fetch r.cozinha")
    List<Restaurante> findAll();

    @Modifying
    @Query(value = "DELETE FROM RESTAURANTES_FORMAS_PAGAMENTO WHERE RESTAURANTES_ID = :restauranteId AND  FORMAS_PAGAMENTO_ID = :formaPagamentoId", nativeQuery = true)
    void desassociarFormaPagamento(@Param("restauranteId") Long restauranteId, @Param("formaPagamentoId") Long formaPagamentoId);

}