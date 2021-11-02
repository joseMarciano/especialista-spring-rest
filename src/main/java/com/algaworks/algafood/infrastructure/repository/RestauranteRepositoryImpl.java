package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CustomizeRestauranteRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

/*
 * Um repositório customizado não precisa implementar o contrato RestauranteRepository desde que tenha o Sufixo IMPL
 * e na interface RestauranteRepository tenha o nome do método que vc deseja customizar
 * */
@Repository
public class RestauranteRepositoryImpl implements CustomizeRestauranteRepository {

    private final EntityManager em;

    public RestauranteRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        var jpql = "from Restaurante where nome like :nome and taxaFrete between :taxaInicial and :taxaFinal";

        return em.createQuery(jpql, Restaurante.class)
                .setParameter("nome", "%" + nome + "%")
                .setParameter("taxaInicial", taxaFreteInicial)
                .setParameter("taxaFinal", taxaFreteFinal)
                .getResultList();

    }
}
