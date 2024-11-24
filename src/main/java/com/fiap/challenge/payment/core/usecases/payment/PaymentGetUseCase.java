package com.fiap.challenge.payment.core.usecases.payment;

import com.fiap.challenge.payment.core.common.exception.EntityNotFoundException;
import com.fiap.challenge.payment.core.domain.Payment;
import com.fiap.challenge.payment.core.gateways.PaymentGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentGetUseCase {

    private final PaymentGateway paymentGateway;

    public Payment execute(Long id) {
        return paymentGateway.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
