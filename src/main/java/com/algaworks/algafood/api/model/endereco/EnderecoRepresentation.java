package com.algaworks.algafood.api.model.endereco;

import lombok.Getter;
import lombok.Setter;

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

}
