package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "RESTAURANTES")
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "NOME")
    private String nome;

    @NotNull
    @PositiveOrZero
    @Column(name = "TAXA_FRETE")
    private BigDecimal taxaFrete;

    @ManyToOne
    @NotNull
    @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    @Valid // validação em cascata
    @JoinColumn(name = "COZINHAS_ID")
    private Cozinha cozinha;

    @ManyToMany
    @JoinTable(
            name = "RESTAURANTES_FORMAS_PAGAMENTO",
            joinColumns = @JoinColumn(name = "RESTAURANTES_ID"),
            inverseJoinColumns = @JoinColumn(name = "FORMAS_PAGAMENTO_ID")
    )
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @OneToMany(mappedBy = "restaurante", orphanRemoval = true)
    private List<Produto> produtos;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(name = "DATA_CADASTRO", columnDefinition = "DATETIME(0)")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "DATA_ATUALIZACAO", columnDefinition = "DATETIME(0)")
    private OffsetDateTime dataAtualizacao;

    @Column(name = "FL_ATIVO")
    private Boolean ativo = Boolean.TRUE;

    public Restaurante ativar() {
        this.setAtivo(true);
        return this;
    }

    public Restaurante inativar() {
        this.setAtivo(false);
        return this;
    }

    public Restaurante associarFormaPagamento(FormaPagamento formaPagamento) {
        this.formasPagamento.add(formaPagamento);
        return this;
    }

}