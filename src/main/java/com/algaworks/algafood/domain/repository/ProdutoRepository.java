package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

    @Query("from Produto p where p.id = :idProduto and p.restaurante.id = :idRestaurante")
    Produto findByRestauranteId(@Param("idProduto") Long idProduto, @Param("idRestaurante") Long idRestaurante);
}
