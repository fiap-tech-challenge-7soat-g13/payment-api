package com.fiap.challenge.payment.core.usecases.payment;

import com.fiap.challenge.payment.core.common.exception.EntityNotFoundException;
import com.fiap.challenge.payment.core.domain.Payment;
import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import com.fiap.challenge.payment.core.gateways.PaymentGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentVerifyUseCase {

    private final PaymentGateway paymentGateway;

    public void execute(UUID uuid, String id) {

        Payment payment = paymentGateway.findByUuid(uuid).orElseThrow(EntityNotFoundException::new);

        if (!payment.getStatus().equals(PaymentStatus.PENDENTE)) {
            return;
        }

        if (payment.getExternalId() == null) {
            payment.setExternalId(id);
            paymentGateway.save(payment);
        }

        PaymentStatus newStatus = paymentGateway.verifyPayment(payment);

        if (newStatus.equals(PaymentStatus.PENDENTE)) {
            return;
        }

        payment.setStatus(newStatus);

        paymentGateway.save(payment);
    }

}
