package com.fiap.challenge.payment.bdd;

import com.fiap.challenge.payment.core.domain.Payment;
import com.fiap.challenge.payment.util.DataHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StepDefinition {

    private Response response;

    private Payment payment;

    @Quando("criar um novo pagamento")
    public void criar_um_novo_pagamento() {
        response = given()
                .contentType(ContentType.JSON)
                .body(DataHelper.createOrderRequest())
                .when()
                .post();
    }

    @Entao("deve retornar sucesso")
    public void deve_retornar_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Entao("deve retornar os dados do pagamento")
    public void deve_retornar_os_dados_do_pagamento() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/PaymentResponse.schema.json"));
    }

    @Dado("um pagamento que existe")
    public void um_pagamento_que_existe() {
        payment = new Payment();
        payment.setId(1L);
        payment.setUuid(UUID.fromString("9ad62775-aea0-48a4-9e0e-42c7438fd844"));
    }

    @Quando("obter o pagamento")
    public void obter_o_pagamento() {
        response = when()
                .get("/{id}", payment.getId());
    }

    @Dado("um pagamento que não existe")
    public void um_pagamento_que_não_existe() {
        payment = new Payment();
        payment.setId(999L);
        payment.setUuid(UUID.randomUUID());
    }

    @Entao("deve retornar não encontrado")
    public void deve_retornar_não_encontrado() {
        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Quando("receber a confirmação de pagamento")
    public void receber_a_confirmação_de_pagamento() {
        response = given()
                .queryParam("id", "id")
                .queryParam("topic", "payment")
                .when()
                .post("/{uuid}/callback", payment.getUuid());
    }

}
