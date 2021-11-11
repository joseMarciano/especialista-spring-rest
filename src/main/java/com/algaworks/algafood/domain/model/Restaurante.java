package com.algaworks.algafood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "RESTAURANTES")
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "TAXA_FRETE")
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name = "COZINHAS_ID")
    private Cozinha cozinha;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "RESTAURANTES_FORMAS_PAGAMENTO",
            joinColumns = @JoinColumn(name = "RESTAURANTES_ID"),
            inverseJoinColumns = @JoinColumn(name = "FORMAS_PAGAMENTO_ID")
    )
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurante", orphanRemoval = true)
    private List<Produto> produtos;

    @Embedded
    @JsonIgnore
    private Endereco endereco;

    @CreationTimestamp
    @Column(name = "DATA_CADASTRO", columnDefinition = "DATETIME(0)")
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "DATA_ATUALIZACAO", columnDefinition = "DATETIME(0)")
    private LocalDateTime dataAtualizacao;

}