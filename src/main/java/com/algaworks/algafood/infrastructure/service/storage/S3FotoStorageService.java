package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.domain.service.FotoStorage;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3FotoStorageService implements FotoStorage {
    @Override
    public void armazenar(NovaFoto novaFoto) {

    }

    @Override
    public void remover(String uri) {

    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }
}
