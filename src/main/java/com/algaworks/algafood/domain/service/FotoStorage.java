package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public interface FotoStorage {

    void armazenar(NovaFoto novaFoto);

    default String gerarNomeArquivo(String nome){
        String nomeArquivo = Optional.ofNullable(nome).orElseThrow(() -> new RuntimeException("Nome de arquivo inválido"));
        return UUID.randomUUID() + nomeArquivo;
    }

    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private InputStream file;
    }

}
