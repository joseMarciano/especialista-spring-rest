package com.algaworks.algafood.domain.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algafood.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "CIDADES")
public class Cidade {

    @EqualsAndHashCode.Include
    @Id
    @NotNull(groups = Groups.CidadeId.class)
    @Column(name = "ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    @NotBlank
    private String nome;

    @ManyToOne
    @JoinColumn(name = "ESTADOS_ID")
    @NotNull
    @Valid
    @ConvertGroup(from = Default.class, to = Groups.EstadoId.class )
    private Estado estado;

}