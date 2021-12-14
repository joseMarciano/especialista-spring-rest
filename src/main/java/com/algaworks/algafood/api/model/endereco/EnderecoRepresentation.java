package com.algaworks.algafood.api.model.endereco;

import com.algaworks.algafood.api.model.cidade.CidadeRepresentation;
import com.algaworks.algafood.domain.model.Cidade;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public interface EnderecoRepresentation {


    @Getter
    @Setter
    class Listagem {
        private String cep;
        private String logradouro;
        private String numero;
        private String complemento;
        private String bairro;
    }

    @Getter
    @Setter
    public class Completa {
        private String cep;
        private String logradouro;
        private String numero;
        private String complemento;
        private String bairro;
        private CidadeRepresentation.Associacao cidade;
    }
}
