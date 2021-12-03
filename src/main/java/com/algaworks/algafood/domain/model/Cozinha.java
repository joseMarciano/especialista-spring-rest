package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "COZINHAS")
public class Cozinha {

    @EqualsAndHashCode.Include
    @Column(name = "ID", updatable = false)
    @Id
    @NotNull(groups = {Groups.CozinhaId.class})
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    @NotBlank
    private String nome;
//
//    @OneToMany(mappedBy = "cozinha", orphanRemoval = true)
//    private List<Restaurante> restaurantes = new ArrayList<>();


}