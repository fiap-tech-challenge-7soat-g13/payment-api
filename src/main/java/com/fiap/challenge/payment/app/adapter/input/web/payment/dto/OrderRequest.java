package com.fiap.challenge.payment.app.adapter.input.web.payment.dto;

import com.fiap.challenge.payment.core.domain.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderRequest {

    private Long id;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderProductRequest> products;
    private CustomerRequest customer;
    private BigDecimal total;

}
