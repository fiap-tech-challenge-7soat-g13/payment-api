package com.fiap.challenge.payment.app.adapter.output.queue.rabbitmq;

import com.fiap.challenge.payment.app.adapter.output.queue.PaymentStatusChangedEvent;
import com.fiap.challenge.payment.core.domain.Payment;
import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PaymentStatusChangedEventDispatcherImplIntegrationTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    private static final String PAYMENT_STATUS_CHANGED_QUEUE = "payment-status-changed";

    @BeforeEach
    void setUp() {
        rabbitAdmin.purgeQueue(PAYMENT_STATUS_CHANGED_QUEUE);
    }

    @Test
    void shouldSendMessage() {

        PaymentStatusChangedEventDispatcherImpl dispatcher = new PaymentStatusChangedEventDispatcherImpl(rabbitTemplate, PAYMENT_STATUS_CHANGED_QUEUE);

        Payment payment = new Payment();

        payment.setId(1L);
        payment.setQrCode("qrCode");
        payment.setStatus(PaymentStatus.APROVADO);

        dispatcher.dispatch(payment);

        PaymentStatusChangedEvent event = (PaymentStatusChangedEvent) rabbitTemplate.receiveAndConvert(PAYMENT_STATUS_CHANGED_QUEUE);

        assertNotNull(event);
        assertEquals(payment.getId(), event.getId());
        assertEquals(payment.getQrCode(), event.getQrCode());
        assertEquals(payment.getStatus(), event.getStatus());
    }

}