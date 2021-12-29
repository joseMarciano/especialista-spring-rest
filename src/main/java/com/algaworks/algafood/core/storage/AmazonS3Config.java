package com.algaworks.algafood.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

    private final StorageProperties storageProperties;

    public AmazonS3Config(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    public AmazonS3 amazonS3() {
        var sThreeData = storageProperties.getSThree();
        var credentilas = new BasicAWSCredentials(sThreeData.getIdChaveAcesso(), sThreeData.getChaveAcessoSecreta());
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentilas))
                .withRegion(sThreeData.getRegiao())
                .build();
    }

}
