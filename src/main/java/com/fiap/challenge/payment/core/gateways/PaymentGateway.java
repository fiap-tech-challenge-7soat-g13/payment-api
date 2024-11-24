package com.fiap.challenge.payment.core.gateways;

import com.fiap.challenge.payment.core.domain.Order;
import com.fiap.challenge.payment.core.domain.Payment;
import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;

import java.util.Optional;
import java.util.UUID;

public interface PaymentGateway {

    Payment save(Payment payment);

    Optional<Payment> findById(Long id);

    Optional<Payment> findByUuid(UUID uuid);

    String generatePayment(UUID paymentUuid, Order order);

    PaymentStatus verifyPayment(Payment payment);

}
