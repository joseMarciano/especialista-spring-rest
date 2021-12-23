package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "FOTOS_PRODUTOS")
public class FotoProduto {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "PRODUTOS_ID")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "CONTENT_TYPE")
    private String contentType;

    @Column(name = "TAMANHO")
    private Long tamanho;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Produto produtos;
}