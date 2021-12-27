package com.algaworks.algafood.api.model.produto;

import com.algaworks.algafood.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FotoProdutoDto {

    private String descricao;

    @FileSize(max = "10KB")
    private MultipartFile file;
}
