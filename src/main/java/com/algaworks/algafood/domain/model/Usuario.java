package com.algaworks.algafood.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
    @NotBlank(message = "É obrigatório informar um nome de usuário")
    private String nome;

    @Column(name = "EMAIL")
    @NotBlank(message = "É obrigatório informar um e-mail de usuário")
    private String email;

    @Column(name = "SENHA")
    @NotBlank(message = "É obrigatório informar uma senha de usuário")
    private String senha;

    @CreationTimestamp
    @Column(name = "DATA_CADASTRO", columnDefinition = "DATETIME(0)", updatable = false)
    private OffsetDateTime dataCadastro;


    @ManyToMany
    @JoinTable(
            name = "USUARIOS_GRUPOS",
            joinColumns = @JoinColumn(name = "USUARIOS_ID"),
            inverseJoinColumns = @JoinColumn(name = "GRUPOS_ID")
    )
    private Set<Grupo> grupos = new HashSet<>();

    public Usuario associar(Grupo... grupos) {
        Iterator<Grupo> iterator = Arrays.stream(grupos).iterator();

        while (iterator.hasNext()) {
            this.grupos.add(iterator.next());
        }

        return this;
    }

    public Usuario desassociar(Grupo... grupos) {
        Iterator<Grupo> iterator = Arrays.stream(grupos).iterator();

        while (iterator.hasNext()) {
            this.grupos.remove(iterator.next());
        }

        return this;
    }
}
