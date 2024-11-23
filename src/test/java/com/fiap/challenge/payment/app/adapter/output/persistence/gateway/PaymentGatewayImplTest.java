package com.fiap.challenge.payment.app.adapter.output.persistence.gateway;

import com.fiap.challenge.payment.app.adapter.output.externalapis.PaymentClient;
import com.fiap.challenge.payment.app.adapter.output.persistence.entity.PaymentEntity;
import com.fiap.challenge.payment.app.adapter.output.persistence.mapper.PaymentMapper;
import com.fiap.challenge.payment.app.adapter.output.persistence.repository.PaymentRepository;
import com.fiap.challenge.payment.core.domain.Order;
import com.fiap.challenge.payment.core.domain.Payment;
import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PaymentGatewayImplTest {

    private final PaymentRepository paymentRepository = mock(PaymentRepository.class);
    private final PaymentClient paymentClient = mock(PaymentClient.class);
    private final PaymentMapper paymentMapper = mock(PaymentMapper.class);

    private final PaymentGatewayImpl paymentGateway = new PaymentGatewayImpl(paymentRepository, paymentClient, paymentMapper);

    @Test
    void shouldSave() {

        Payment payment = new Payment();
        PaymentEntity paymentEntity = new PaymentEntity();
        PaymentEntity savedPaymentEntity = new PaymentEntity();
        Payment expected = new Payment();

        when(paymentMapper.toPaymentEntity(payment)).thenReturn(paymentEntity);
        when(paymentRepository.save(paymentEntity)).thenReturn(savedPaymentEntity);
        when(paymentMapper.toPayment(savedPaymentEntity)).thenReturn(expected);

        Payment actual = paymentGateway.save(payment);

        verify(paymentMapper).toPaymentEntity(payment);
        verify(paymentRepository).save(paymentEntity);
        verify(paymentMapper).toPayment(savedPaymentEntity);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {

        Long id = 1L;
        PaymentEntity paymentEntity = new PaymentEntity();
        Payment expected = new Payment();

        when(paymentRepository.findById(id)).thenReturn(Optional.of(paymentEntity));
        when(paymentMapper.toPayment(paymentEntity)).thenReturn(expected);

        Optional<Payment> actual = paymentGateway.findById(id);

        verify(paymentRepository).findById(id);
        verify(paymentMapper).toPayment(paymentEntity);

        assertEquals(Optional.of(expected), actual);
    }

    @Test
    void shouldNotFindById() {

        Long id = 1L;

        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Payment> actual = paymentGateway.findById(id);

        verify(paymentRepository).findById(id);
        verify(paymentMapper, never()).toPayment(any());

        assertEquals(Optional.empty(), actual);
    }

    @Test
    void shouldFindByUuid() {

        UUID uuid = UUID.randomUUID();
        PaymentEntity paymentEntity = new PaymentEntity();
        Payment expected = new Payment();

        when(paymentRepository.findByUuid(uuid)).thenReturn(Optional.of(paymentEntity));
        when(paymentMapper.toPayment(paymentEntity)).thenReturn(expected);

        Optional<Payment> actual = paymentGateway.findByUuid(uuid);

        verify(paymentRepository).findByUuid(uuid);
        verify(paymentMapper).toPayment(paymentEntity);

        assertEquals(Optional.of(expected), actual);
    }

    @Test
    void shouldNotFindByUuid() {

        UUID uuid = UUID.randomUUID();

        when(paymentRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        Optional<Payment> actual = paymentGateway.findByUuid(uuid);

        verify(paymentRepository).findByUuid(uuid);
        verify(paymentMapper, never()).toPayment(any());

        assertEquals(Optional.empty(), actual);
    }

    @Test
    void shouldGeneratePayment() {

        UUID paymentUuid = UUID.randomUUID();
        Order order = new Order();
        String expectedQrCode = "qrCode";

        when(paymentClient.createPayment(paymentUuid, order)).thenReturn(expectedQrCode);

        String actualQrCode = paymentGateway.generatePayment(paymentUuid, order);

        verify(paymentClient).createPayment(paymentUuid, order);

        assertEquals(expectedQrCode, actualQrCode);
    }

    @Test
    void shouldVerifyPayment() {

        Payment payment = new Payment();

        payment.setExternalId("externalId");

        PaymentStatus expected = PaymentStatus.APROVADO;

        when(paymentClient.verifyPayment(payment.getExternalId())).thenReturn(expected);

        PaymentStatus actual = paymentGateway.verifyPayment(payment);

        verify(paymentClient).verifyPayment(payment.getExternalId());

        assertEquals(expected, actual);
    }

}
