package com.algaworks.algafood.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@ConfigurationProperties(prefix = "algafood.storage")
@Getter
@Setter
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();
    private TipoStorage tipoStorage = TipoStorage.LOCAL;


    enum TipoStorage {
        LOCAL,
        S3
    }


    @Getter
    @Setter
    public class Local {
        private Path diretorioFotos;
    }

    @Getter
    @Setter
    public class S3 {
        private String bucket;
        private String idChaveAcesso;
        private String chaveAcessoSecreta;
        private String diretorio;
        private Regions regiao;
    }
}
// Essa classe segue o caminho das propriedades do application.yml
// Por exemplo: eu tenho no @ConfigurationProperties o meu prefixo algafood.storage
//Depois eu tenho a propriedade "local" que representa o "local" do meu application e dentro da classe Local eu tenho
// o diretorioFotos que representa o diretorio-fotos do meu application.
/*
algafood:
        storage:
        s3:
        bucket: app.algafood.cloud
        id-chave-acesso: AKIAWP7LIUDW5S7DZPUG,
        chave-acesso-secreta: 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,
        diretorio: catalogo,
        regiao: us-east-1

        local:
        diretorio-fotos: "/home/jose/Desktop"
*/