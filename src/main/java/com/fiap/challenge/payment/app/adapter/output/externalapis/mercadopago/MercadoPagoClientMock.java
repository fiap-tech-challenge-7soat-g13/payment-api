package com.fiap.challenge.payment.app.adapter.output.externalapis.mercadopago;

import com.fiap.challenge.payment.app.adapter.output.externalapis.PaymentClient;
import com.fiap.challenge.payment.core.domain.Order;
import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Profile("test")
@RequiredArgsConstructor
public class MercadoPagoClientMock implements PaymentClient {

    @Override
    public String createPayment(UUID paymentUuid, Order order) {
        return "00020101021243650016COM.MERCADOLIBRE020130636dc0c2d38-6be5-4959-8a46-5801afd151115204000053039865802BR5909Test Test6009SAO PAULO62070503***6304FCE2";
    }

    @Override
    public PaymentStatus verifyPayment(String paymentId) {
        return PaymentStatus.APROVADO;
    }

}
