package com.fiap.challenge.payment.core.domain;

import com.fiap.challenge.payment.core.domain.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Order {

    private Long id;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private Customer customer;
    private BigDecimal total;
    private List<OrderProduct> products;

}
