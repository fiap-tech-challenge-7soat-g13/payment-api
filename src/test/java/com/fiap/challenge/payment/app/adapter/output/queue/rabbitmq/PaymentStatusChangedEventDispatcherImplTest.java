package com.fiap.challenge.payment.app.adapter.output.queue.rabbitmq;

import com.fiap.challenge.payment.app.adapter.output.queue.PaymentStatusChangedEvent;
import com.fiap.challenge.payment.core.domain.Payment;
import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PaymentStatusChangedEventDispatcherImplTest {

    private final RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
    private final String paymentStatusChangedQueue = "paymentStatusChangedQueue";

    private final PaymentStatusChangedEventDispatcherImpl paymentStatusChangedEventDispatcher = new PaymentStatusChangedEventDispatcherImpl(rabbitTemplate, paymentStatusChangedQueue);

    @Test
    void shouldDispatch() {

        Payment payment = new Payment();

        payment.setId(1L);
        payment.setQrCode("qrCode");
        payment.setStatus(PaymentStatus.APROVADO);

        paymentStatusChangedEventDispatcher.dispatch(payment);

        ArgumentCaptor<PaymentStatusChangedEvent> eventArgumentCaptor = ArgumentCaptor.forClass(PaymentStatusChangedEvent.class);

        verify(rabbitTemplate).convertAndSend(eq(paymentStatusChangedQueue), eventArgumentCaptor.capture());

        assertEquals(payment.getId(), eventArgumentCaptor.getValue().getId());
        assertEquals(payment.getQrCode(), eventArgumentCaptor.getValue().getQrCode());
        assertEquals(payment.getStatus(), eventArgumentCaptor.getValue().getStatus());
    }

}