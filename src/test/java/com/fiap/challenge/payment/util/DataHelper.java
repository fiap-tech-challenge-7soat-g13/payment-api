package com.fiap.challenge.payment.util;

import com.fiap.challenge.payment.app.adapter.input.web.payment.dto.CustomerRequest;
import com.fiap.challenge.payment.app.adapter.input.web.payment.dto.OrderProductRequest;
import com.fiap.challenge.payment.app.adapter.input.web.payment.dto.OrderRequest;
import com.fiap.challenge.payment.app.adapter.input.web.payment.dto.ProductRequest;
import com.fiap.challenge.payment.core.domain.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataHelper {

    public static OrderRequest createOrderRequest() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setId(1L);
        orderRequest.setStatus(OrderStatus.CRIADO);
        orderRequest.setCreatedAt(LocalDateTime.now());
        orderRequest.setTotal(BigDecimal.TWO);
        orderRequest.setCustomer(createCustomerRequest());
        orderRequest.setProducts(List.of(createOrderProductRequest()));
        return orderRequest;
    }

    public static CustomerRequest createCustomerRequest() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setId(1L);
        customerRequest.setName("Bill Gates");
        customerRequest.setDocument("44867508020");
        customerRequest.setEmail("bill.gates@microsoft.com");
        return customerRequest;
    }

    public static OrderProductRequest createOrderProductRequest() {
        OrderProductRequest orderProductRequest = new OrderProductRequest();
        orderProductRequest.setId(1L);
        orderProductRequest.setQuantity(2);
        orderProductRequest.setPrice(BigDecimal.ONE);
        orderProductRequest.setProduct(createProductRequest());
        return orderProductRequest;
    }

    public static ProductRequest createProductRequest() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("X-burger");
        return productRequest;
    }

}
