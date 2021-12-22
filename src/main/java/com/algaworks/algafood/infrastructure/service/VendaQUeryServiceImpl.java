package com.algaworks.algafood.infrastructure.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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


        query.select(selection);
        query.groupBy(functionDate);
        return entityManager.createQuery(query).getResultList();
    }
}
