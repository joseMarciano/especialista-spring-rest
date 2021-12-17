package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "PEDIDOS")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {

    @Id
    @Column(name = "ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "SUB_TOTAL")
    private BigDecimal subTotal;

    @Column(name = "TAXA_FRETE")
    private BigDecimal taxaFrete;

    @Column(name = "VALOR_TOTAL")
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(name = "DATA_CRIACAO", updatable = false, columnDefinition = "DATETIME(0)")
    private OffsetDateTime dataCriacao;

    @Column(name = "DATA_CONFIRMACAO", columnDefinition = "DATETIME(0)")
    private OffsetDateTime dataConfirmacao;

    @Column(name = "DATA_CANCELAMENTO", columnDefinition = "DATETIME(0)")
    private OffsetDateTime dataCancelamento;

    @Column(name = "DATA_ENTREGA", columnDefinition = "DATETIME(0)")
    private OffsetDateTime dataEntrega;

    @Column(name = "STATUS_PEDIDO")
    private StatusPedido statusPedido;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USUARIOS_ID", referencedColumnName = "ID")
    private Usuario cliente;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "RESTAURANTES_ID", referencedColumnName = "ID")
    private Restaurante restaurante;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "FORMAS_PAGAMENTO_ID", referencedColumnName = "ID")
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "pedido", orphanRemoval = true)
    List<ItemPedido> itensPedido = new ArrayList<>();

    @Embedded
    Endereco endereco;

}
