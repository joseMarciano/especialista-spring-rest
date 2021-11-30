package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Test
    public void deveRetornarStatus200QuandoConsultarCozinhas() {
        given()
                .basePath("/cozinhas")
                .port(port)
                .accept(ContentType.JSON)
                .when().get().then().statusCode(HttpStatus.OK.value());
    }
//    @Autowired
//    private CozinhaService service;
//
//
//    @Test
//    public void testarCadastroCozinhaComSucesso() {
//        Cozinha cozinha = new Cozinha();
//        cozinha.setNome("Cozinha teste");
//
//        cozinha = service.save(cozinha);
//
//        assertNotNull(cozinha);
//        assertNotNull(cozinha.getId());
//    }
//
//    @Test
//    public void testarCadastroCozinhaSemNome() {
//        assertThrows(ConstraintViolationException.class, () -> service.save(new Cozinha()));
//    }
//
//    @Test
//    public void deveFalharQuandoExcluirCozinhaEmUso() {
//        assertThrows(EntidadeEmUsoException.class, () -> service.remove(1L));
//    }
//
//    @Test
//    public void deveFalharQuandoExcluirCozinhaInexistente() {
//        assertThrows(CozinhaNaoEncontradoException.class, () -> service.remove(9999L));
//    }

}
