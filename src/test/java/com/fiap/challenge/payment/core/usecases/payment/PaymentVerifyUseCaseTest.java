package com.fiap.challenge.payment.core.usecases.payment;

import com.fiap.challenge.payment.app.adapter.output.queue.PaymentStatusChangedEventDispatcher;
import com.fiap.challenge.payment.core.domain.Payment;
import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import com.fiap.challenge.payment.core.gateways.PaymentGateway;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PaymentVerifyUseCaseTest {

    private final PaymentGateway paymentGateway = mock(PaymentGateway.class);
    private final PaymentStatusChangedEventDispatcher paymentStatusChangedEventDispatcher = mock(PaymentStatusChangedEventDispatcher.class);

    private final PaymentVerifyUseCase paymentVerifyUseCase = new PaymentVerifyUseCase(paymentGateway, paymentStatusChangedEventDispatcher);

    @Test
    void shouldNotSaveAnything() {

        String id = "1";
        UUID uuid = UUID.randomUUID();

        Payment payment = new Payment();

        payment.setStatus(PaymentStatus.FALHADO);

        when(paymentGateway.findByUuid(uuid)).thenReturn(Optional.of(payment));

        paymentVerifyUseCase.execute(uuid, id);

        verify(paymentGateway, never()).save(any());
        verify(paymentGateway, never()).verifyPayment(any());
    }

    @Test
    void shouldSetExternalId() {

        String id = "1";
        UUID uuid = UUID.randomUUID();

        Payment payment = new Payment();

        payment.setStatus(PaymentStatus.PENDENTE);

        when(paymentGateway.findByUuid(uuid)).thenReturn(Optional.of(payment));
        when(paymentGateway.verifyPayment(payment)).thenReturn(PaymentStatus.PENDENTE);

        paymentVerifyUseCase.execute(uuid, id);

        verify(paymentGateway).save(payment);

        assertEquals(id, payment.getExternalId());
    }

    @Test
    void shouldUpdatePayment() {

        String id = "1";
        UUID uuid = UUID.randomUUID();

        Payment payment = new Payment();

        payment.setExternalId(id);
        payment.setStatus(PaymentStatus.PENDENTE);

        when(paymentGateway.findByUuid(uuid)).thenReturn(Optional.of(payment));
        when(paymentGateway.verifyPayment(payment)).thenReturn(PaymentStatus.APROVADO);

        paymentVerifyUseCase.execute(uuid, id);

        verify(paymentGateway).save(payment);

        assertEquals(PaymentStatus.APROVADO, payment.getStatus());
    }

}