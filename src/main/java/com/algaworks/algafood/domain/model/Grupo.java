package com.algaworks.algafood.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GRUPOS")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "DATETIME(0)")
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "DATETIME(0)")
    private LocalDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(
            name = "GRUPOS_PERMISSOES",
            joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )
    private List<Permissao> permissoes = new ArrayList<>();

}
