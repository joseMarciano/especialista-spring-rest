package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "COZINHAS")
public class Cozinha {

    @EqualsAndHashCode.Include
    @Column(name = "ID", updatable = false)
    @Id
    @NotNull(message = "É obrigatório informar um identificador para cozinha")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String nome;
//
//    @OneToMany(mappedBy = "cozinha", orphanRemoval = true)
//    private List<Restaurante> restaurantes = new ArrayList<>();


}