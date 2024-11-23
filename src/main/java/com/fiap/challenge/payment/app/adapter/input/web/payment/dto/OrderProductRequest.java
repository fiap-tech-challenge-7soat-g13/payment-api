package com.fiap.challenge.payment.app.adapter.input.web.payment.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderProductRequest {

    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private ProductRequest product;

}
