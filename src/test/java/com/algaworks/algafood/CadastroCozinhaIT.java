package com.algaworks.algafood;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

//    @Autowired
//    private Flyway flyway;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        //Usado para executar um after migrate(arquivo que pode ser criado por mim para executar comandos apos migrations) para restaurar o banco para cada teste realizado
//        flyway.migrate();
    }

    @Test
    public void deveRetornarStatus200QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when().get().then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConter4QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when().get().then()
                .body("nome", Matchers.hasSize(5));
    }

    @Test
    public void deveRetornarStatus201QuandoCadastrarCozinha() throws IOException {
        String jsonBody = new String(Files.readAllBytes(Paths.get("src/test/resources/cadastroCozinhaBody.json")));
        given()
                .body(jsonBody)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());

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
