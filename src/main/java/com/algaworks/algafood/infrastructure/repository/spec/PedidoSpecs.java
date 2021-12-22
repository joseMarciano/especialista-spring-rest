package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (filtro.getClienteId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("cliente"), filtro.getClienteId()));
            }

            if (filtro.getRestauranteId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("restaurante"), filtro.getRestauranteId()));
            }




            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
