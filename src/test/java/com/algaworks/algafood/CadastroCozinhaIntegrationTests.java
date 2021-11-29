package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CadastroCozinhaIntegrationTests {

    @Autowired
    private CozinhaService service;


    @Test
    public void testarCadastroCozinhaComSucesso() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Cozinha teste");

        cozinha = service.save(cozinha);

        assertNotNull(cozinha);
        assertNotNull(cozinha.getId());
    }

    @Test
    public void testarCadastroCozinhaSemNome() {
        assertThrows(ConstraintViolationException.class, () -> service.save(new Cozinha()));
    }

}
