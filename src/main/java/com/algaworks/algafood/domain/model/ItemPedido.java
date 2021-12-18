package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "ITENS_PEDIDO")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "QUANTIDADE")
    @NotNull(message = "É obrigatório informar uma quantidade de cada item")
    private Integer quantidade;

    @Column(name = "PRECO_UNITARIO")
    private BigDecimal precoUnitario;

    @Column(name = "PRECO_TOTAL")
    private BigDecimal precoTotal;

    @Column(name = "OBSERVACAO")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "PRODUTOS_ID", referencedColumnName = "ID")
    @NotNull(message = "É obrigatório informar um produto para o item")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "PEDIDOS_ID", referencedColumnName = "ID")
    private Pedido pedido;

}
