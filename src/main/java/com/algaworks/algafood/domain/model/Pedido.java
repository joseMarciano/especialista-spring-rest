package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.algaworks.algafood.domain.model.StatusPedido.*;
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
    @NotNull(message = "É obrigatório informar um cliente para o pedido")
    @JoinColumn(name = "USUARIOS_ID", referencedColumnName = "ID")
    private Usuario cliente;

    @ManyToOne(fetch = LAZY)
    @NotNull(message = "É obrigatório informar um restaurante para o pedido")
    @JoinColumn(name = "RESTAURANTES_ID", referencedColumnName = "ID")
    private Restaurante restaurante;

    @ManyToOne(fetch = LAZY)
    @NotNull(message = "É obrigatório informar uma forma de pagamento para o pedido")
    @JoinColumn(name = "FORMAS_PAGAMENTO_ID", referencedColumnName = "ID")
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "pedido", orphanRemoval = true, cascade = CascadeType.ALL)
    @Size(min = 1, message = "É obrigatório ter pelo menos {min} item no pedido")
    List<ItemPedido> itensPedido = new ArrayList<>();

    @Embedded
    Endereco endereco;

    public void calcularTotal() {
        this.itensPedido.forEach(item -> {
            item.setPrecoTotal(item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
        });
        BigDecimal total = this.itensPedido.stream().map(ItemPedido::getPrecoTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        this.setSubTotal(total);
        this.setValorTotal(total.add(this.getTaxaFrete()));
    }

    public boolean tramitarStatus(StatusPedido proximoStatus) {
        StatusPedido statusAtual = this.statusPedido;


        if (CRIADO.equals(statusAtual) && !CONFIRMADO.equals(proximoStatus)) return false;
        if (statusAtual != null && !CRIADO.equals(statusAtual) && CRIADO.equals(proximoStatus)) return false;
        if (ENTREGUE.equals(statusAtual) && CANCELADO.equals(proximoStatus)) return false;
        if (CANCELADO.equals(statusAtual) && CONFIRMADO.equals(proximoStatus)) return false;

        if (CRIADO.equals(proximoStatus)) {
            this.setDataCriacao(OffsetDateTime.now());
        }

        if (CONFIRMADO.equals(proximoStatus)) {
            this.setDataConfirmacao(OffsetDateTime.now());
        }

        if (ENTREGUE.equals(proximoStatus)) {
            this.setDataEntrega(OffsetDateTime.now());
        }

        if (CANCELADO.equals(proximoStatus)) {
            this.setDataCancelamento(OffsetDateTime.now());
        }


        this.setStatusPedido(proximoStatus);
        return true;
    }
}
