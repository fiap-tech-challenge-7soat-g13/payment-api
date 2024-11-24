package com.fiap.challenge.payment.core.usecases.payment;

import com.fiap.challenge.payment.core.domain.Order;
import com.fiap.challenge.payment.core.domain.Payment;
import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import com.fiap.challenge.payment.core.gateways.PaymentGateway;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentCreateUseCaseTest {

    private final PaymentGateway paymentGateway = mock(PaymentGateway.class);

    private final PaymentCreateUseCase paymentCreateUseCase = new PaymentCreateUseCase(paymentGateway);

    @Test
    void shouldCreate() {

        Order order = new Order();
        String qrCode = "qrCode";
        Payment expected = new Payment();

        when(paymentGateway.generatePayment(any(), eq(order))).thenReturn(qrCode);
        when(paymentGateway.save(any())).thenReturn(expected);

        Payment actual = paymentCreateUseCase.execute(order);

        ArgumentCaptor<UUID> uuidArgument = ArgumentCaptor.forClass(UUID.class);
        ArgumentCaptor<Payment> paymentArgument = ArgumentCaptor.forClass(Payment.class);

        verify(paymentGateway).generatePayment(uuidArgument.capture(), eq(order));
        verify(paymentGateway).save(paymentArgument.capture());

        assertEquals(expected, actual);

        assertNotNull(uuidArgument.getValue());

        assertEquals(uuidArgument.getValue(), paymentArgument.getValue().getUuid());
        assertEquals(qrCode, paymentArgument.getValue().getQrCode());
        assertEquals(PaymentStatus.PENDENTE, paymentArgument.getValue().getStatus());
    }

}