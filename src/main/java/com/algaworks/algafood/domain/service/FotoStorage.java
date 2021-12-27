package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

public interface FotoStorage {

    void armazenar(NovaFoto novaFoto);

    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private InputStream file;
    }

}
