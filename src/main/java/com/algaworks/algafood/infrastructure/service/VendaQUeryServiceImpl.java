package com.algaworks.algafood.infrastructure.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.algaworks.algafood.domain.model.StatusPedido.CONFIRMADO;
import static com.algaworks.algafood.domain.model.StatusPedido.ENTREGUE;

@Service
public class VendaQUeryServiceImpl implements VendaQueryService {

    private final EntityManager entityManager;

    public VendaQUeryServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VendaDiaria> query =
                criteriaBuilder.createQuery(VendaDiaria.class);
        Root<Pedido> from = query.from(Pedido.class);

        Expression<Date> functionDate = criteriaBuilder.function("date", Date.class, from.get("dataCriacao"));

        var selection = criteriaBuilder.construct(VendaDiaria.class,
                functionDate,
                criteriaBuilder.count(from.get("id")),
                criteriaBuilder.sum(from.get("valorTotal"))
        );

        query.where(criteriaBuilder.in(from.get("statusPedido")).value(CONFIRMADO).value(ENTREGUE));

        Predicate[] predicateFilter = getFilter(filter,criteriaBuilder,from);


        query.select(selection);
        query.groupBy(functionDate);

        query.where(predicateFilter);

        return entityManager.createQuery(query).getResultList();
    }

    private Predicate[] getFilter(VendaDiariaFilter filter, CriteriaBuilder criteriaBuilder, Root<Pedido> from) {
        var predicates = new ArrayList<Predicate>();

        if(filter.getRestauranteId() != null){
            predicates.add(criteriaBuilder.equal(from.get("restaurante"),filter.getRestauranteId()));
        }

        if(filter.getDataCriacaoInicio() != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("dataCriacao"),filter.getDataCriacaoInicio()));
        }


        if(filter.getDataCriacaoFim() != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("dataCriacao"),filter.getDataCriacaoFim()));
        }


        return predicates.toArray(new Predicate[]{});
    }
}
