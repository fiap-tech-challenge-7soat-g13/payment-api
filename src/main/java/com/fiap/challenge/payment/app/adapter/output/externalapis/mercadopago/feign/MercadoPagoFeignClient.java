package com.fiap.challenge.payment.app.adapter.output.externalapis.mercadopago.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mercadopago")
public interface MercadoPagoFeignClient {

    @PostMapping("/instore/orders/qr/seller/collectors/{userId}/pos/{posId}/qrs")
    CreateOrderResponse createOrder(@PathVariable String userId, @PathVariable String posId, CreateOrderRequest orderRequest);

    @GetMapping("/merchant_orders/{orderId}")
    GetOrderResponse getOrder(@PathVariable String orderId);

}
