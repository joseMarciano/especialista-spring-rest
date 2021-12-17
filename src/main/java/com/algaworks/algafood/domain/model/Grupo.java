package com.algaworks.algafood.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
    @NotNull(message = "É obrigatório informar um nome para o grupo")
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "GRUPOS_PERMISSOES",
            joinColumns = @JoinColumn(name = "GRUPOS_ID"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSOES_ID")
    )
    private Set<Permissao> permissoes = new HashSet<>();

    public Grupo associar(Permissao... p) {
        Iterator<Permissao> iterator = Arrays.stream(p).iterator();

        while (iterator.hasNext()) {
            this.permissoes.add(iterator.next());
        }

        return this;
    }

    public Grupo desassociar(Permissao... p) {
        Iterator<Permissao> iterator = Arrays.stream(p).iterator();

        while (iterator.hasNext()) {
            this.permissoes.remove(iterator.next());
        }

        return this;
    }
}
