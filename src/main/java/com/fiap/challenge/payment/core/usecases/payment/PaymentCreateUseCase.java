package com.fiap.challenge.payment.core.usecases.payment;

import com.fiap.challenge.payment.core.domain.Order;
import com.fiap.challenge.payment.core.domain.Payment;
import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import com.fiap.challenge.payment.core.gateways.PaymentGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentCreateUseCase {

    private final PaymentGateway paymentGateway;

    public Payment execute(Order order) {

        Payment payment = new Payment();

        payment.setUuid(UUID.randomUUID());
        payment.setQrCode(paymentGateway.generatePayment(payment.getUuid(), order));
        payment.setStatus(PaymentStatus.PENDENTE);

        return paymentGateway.save(payment);
    }

}
