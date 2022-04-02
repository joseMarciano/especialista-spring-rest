package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.domain.service.FotoStorage;
import com.algaworks.algafood.infrastructure.service.storage.LocalFotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.algaworks.algafood.core.storage.StorageProperties.TipoStorage.*;

@Configuration
public class FotoStorageConfig {

    private final StorageProperties storageProperties;

    public FotoStorageConfig(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    @ConditionalOnProperty(name = "algafood.storage.tipo-storage", havingValue = "s3")
    public AmazonS3 amazonS3() {
        var sThreeData = storageProperties.getS3();
        var credentilas = new BasicAWSCredentials(sThreeData.getIdChaveAcesso(), sThreeData.getChaveAcessoSecreta());
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentilas))
                .withRegion(sThreeData.getRegiao())
                .build();
    }

    @Bean
    public FotoStorage fotoStorageService(){

        if(S3.equals(storageProperties.getTipoStorage())){
            return new S3FotoStorageService();
        }


        return new LocalFotoStorageService();
    }

}
