package com.algaworks.algafood.infrastructure.repository;


import com.algaworks.algafood.domain.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

    private EntityManager em;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
                                   EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.em = entityManager;
    }

    @Override
    public Optional<T> findFirst() {
        var jpql = "from " + getDomainClass().getName();

        T resultList = em.createQuery(jpql, getDomainClass())
                .setMaxResults(1)
                .getSingleResult();

        return Optional.ofNullable(resultList);
    }

//    @Override
//    public T buscarOuFalhar(Long id) {
//        var jpql = "from " + getDomainClass().getName() + " where id = " + id;
//
//        try {
//            return em.createQuery(jpql, getDomainClass())
//                    .getSingleResult();
//        } catch (NoResultException ex) {
//            throw new EntidadeNaoEncontradaException(String.format("%s de id %s não foi encontrada", getDomainClass().getName(), id));
//        }
//
//    }
}
