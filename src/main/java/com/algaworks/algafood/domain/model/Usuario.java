package com.algaworks.algafood.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USUARIOS")
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SENHA")
    private String senha;

    @CreationTimestamp
    @Column(name = "DATA_CADASTRO", columnDefinition = "DATETIME(0)")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "DATA_ATUALIZACAO", columnDefinition = "DATETIME(0)")
    private OffsetDateTime dataAtualizacao;


    @ManyToMany
    @JoinTable(
            name = "USUARIOS_PERMISSOES",
            joinColumns = @JoinColumn(name = "USUARIOS_ID"),
            inverseJoinColumns = @JoinColumn(name = "GRUPOS_ID")
    )
    private List<Grupo> grupos = new ArrayList<>();

}
