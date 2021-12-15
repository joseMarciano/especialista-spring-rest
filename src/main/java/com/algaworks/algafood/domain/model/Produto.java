package com.algaworks.algafood.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUTOS")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NOME")
    @NotBlank(message = "É obrigatório informar um nome para o restaurante")
    private String nome;

    @NotBlank(message = "É obrigatório informar uma descrição para o restaurante")
    @Column(name = "DESCRICAO")
    private String descricao;

    @NotNull(message = "É obrigatório informar um preço para o restaurante")
    @Min(value = 0, message = "O preço deve ser maior do que zero")
    @Column(name = "PRECO")
    private BigDecimal preco;

    @Column(name = "ATIVO")
    @NotNull(message = "É obrigatório informar a status de ativação do produto")
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "RESTAURANTES_ID")
    private Restaurante restaurante;
}
