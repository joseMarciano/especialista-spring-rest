package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
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
    private Integer quantidade;

    @Column(name = "PRECO_UNITARIO")
    private BigDecimal precoUnitario;

    @Column(name = "PRECO_TOTAL")
    private BigDecimal precoTotal;

    @Column(name = "OBSERVACAO")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "PRODUTOS_ID", referencedColumnName = "ID")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "PEDIDOS_ID", referencedColumnName = "ID")
    private Pedido pedido;

}
