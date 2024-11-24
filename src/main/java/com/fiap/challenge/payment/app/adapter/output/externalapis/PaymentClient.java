package com.fiap.challenge.payment.app.adapter.output.externalapis;

import com.fiap.challenge.payment.core.domain.Order;
import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;

import java.util.UUID;

public interface PaymentClient {

    String createPayment(UUID paymentUuid, Order order);

    PaymentStatus verifyPayment(String paymentId);

}
