package com.algaworks.algafood.api.model.produto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FotoProdutoDto {

    private String descricao;
    private MultipartFile file;
}
