package com.algaworks.algafood.api.model.restaurante;

import com.algaworks.algafood.api.model.endereco.EnderecoRepresentation;
import com.algaworks.algafood.api.model.produto.ProdutoRepresentation;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public interface RestauranteRepresentation {

    @Getter
    @Setter
    class RestauranteCompleta {
        private Long id;
        private String nome;
        private BigDecimal taxaFrete;
        private Cozinha cozinha;
        private List<ProdutoRepresentation.RestauranteAssociacao> produtos = new ArrayList<>();
        private Endereco endereco;
        private OffsetDateTime dataCadastro;
        private OffsetDateTime dataAtualizacao;
        private Boolean ativo;

    }

    @Getter
    @Setter
    class RestauranteListagem {
        private Long id;
        private String nome;
        private Boolean ativo;
        private EnderecoRepresentation.Listagem endereco;
    }


}
