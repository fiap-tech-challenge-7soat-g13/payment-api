package com.fiap.challenge.payment.app.adapter.output.externalapis.mercadopago.feign;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetOrderResponse {

    private String id;
    private String externalReference;
    private String orderStatus;

}
