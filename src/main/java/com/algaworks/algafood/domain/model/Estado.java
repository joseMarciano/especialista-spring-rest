package com.algaworks.algafood.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ESTADOS")
public class Estado {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull( groups = Groups.EstadoId.class)
    private Long id;

    @Column(name = "NOME")
    @NotBlank
    private String nome;

}