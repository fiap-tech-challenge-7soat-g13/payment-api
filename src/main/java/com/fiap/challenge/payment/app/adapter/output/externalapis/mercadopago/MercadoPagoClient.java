package com.fiap.challenge.payment.app.adapter.output.externalapis.mercadopago;

import com.fiap.challenge.payment.app.adapter.output.externalapis.PaymentClient;
import com.fiap.challenge.payment.app.adapter.output.externalapis.mercadopago.feign.*;
import com.fiap.challenge.payment.core.domain.Order;
import com.fiap.challenge.payment.core.domain.OrderProduct;
import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Profile("!test")
@RequiredArgsConstructor
public class MercadoPagoClient implements PaymentClient {

    @Value("${application.mercadopago.userId}")
    private String userId;

    @Value("${application.mercadopago.posId}")
    private String posId;

    @Value("${application.mercadopago.callbackUrl}")
    private String callbackUrl;

    private final MercadoPagoFeignClient client;

    @Override
    public String createPayment(UUID paymentUuid, Order order) {
        CreateOrderRequest request = toCreateOrderRequest(paymentUuid, order);
        CreateOrderResponse response = client.createOrder(userId, posId, request);
        return response.getQrData();
    }

    @Override
    public PaymentStatus verifyPayment(String paymentId) {
        GetOrderResponse res = this.client.getOrder(paymentId);
        return switch (res.getOrderStatus()) {
            case "paid" -> PaymentStatus.APROVADO;
            case "payment_required", "payment_in_process" -> PaymentStatus.PENDENTE;
            default -> PaymentStatus.FALHADO;
        };
    }

    private CreateOrderRequest toCreateOrderRequest(UUID paymentUuid, Order order) {
        return CreateOrderRequest.builder()
                .title(String.format("Order #%s", order.getId()))
                .description(String.format("Order created at %s by customer %s", order.getCreatedAt(), order.getCustomer().getDocument()))
                .totalAmount(order.getTotal())
                .externalReference(order.getId().toString())
                .notificationUrl(callbackUrl + String.format("/%s/callback", paymentUuid))
                .items(order.getProducts().stream().map(this::toCreateOrderItemRequest).toList())
                .build();
    }

    private CreateOrderItemRequest toCreateOrderItemRequest(OrderProduct orderProduct) {
        return CreateOrderItemRequest.builder()
                .title(orderProduct.getProduct().getName())
                .quantity((long) orderProduct.getQuantity())
                .unitPrice(orderProduct.getPrice())
                .totalAmount(orderProduct.getPrice().multiply(BigDecimal.valueOf(orderProduct.getQuantity())))
                .unitMeasure("unit")
                .build();
    }

}
