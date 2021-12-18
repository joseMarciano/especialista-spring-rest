package com.algaworks.algafood.api.model.formapagamento;

import lombok.Getter;
import lombok.Setter;

public interface FormaPagamentoRepresentation {

    @Getter
    @Setter
    class Completa {
        private Long id;
        private String descricao;
    }

    @Getter
    @Setter
    class Listagem {
        private Long id;
        private String descricao;
    }

    @Getter
    @Setter
    class AssociacaoPedido {
        private Long id;
        private String descricao;
    }

    @Getter
    @Setter
    class Associacao {
        private Long id;
    }
}
