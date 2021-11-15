package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    ERRO_DE_SISTEMA("/erro-de-sistema","Ocorreu um erro interno"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Url params inválidos"),
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    ERRO_NEGOCIO("/erro-negocio", "Erro de negócio"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso");


    private String title;
    private String uri;

    ProblemType(String uri, String title) {
        this.title = "https://algafood.com.br" + uri;
        this.uri = uri;
    }
}
