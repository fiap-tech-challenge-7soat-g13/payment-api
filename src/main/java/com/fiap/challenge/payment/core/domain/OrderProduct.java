package com.fiap.challenge.payment.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderProduct {

    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private Product product;

}
