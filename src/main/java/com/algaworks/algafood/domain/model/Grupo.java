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

    @Column(name = "NOME")
    private String nome;

    @CreationTimestamp
    @Column(name = "DATA_CADASTRO", columnDefinition = "DATETIME(0)")
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "DATA_ATUALIZACAO", columnDefinition = "DATETIME(0)")
    private LocalDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(
            name = "GRUPOS_PERMISSOES",
            joinColumns = @JoinColumn(name = "GRUPOS_ID"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSOES_ID")
    )
    private List<Permissao> permissoes = new ArrayList<>();

}
