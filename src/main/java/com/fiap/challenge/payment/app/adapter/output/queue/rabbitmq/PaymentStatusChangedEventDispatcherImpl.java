package com.fiap.challenge.payment.app.adapter.output.queue.rabbitmq;

import com.fiap.challenge.payment.app.adapter.output.queue.PaymentStatusChangedEvent;
import com.fiap.challenge.payment.app.adapter.output.queue.PaymentStatusChangedEventDispatcher;
import com.fiap.challenge.payment.core.domain.Payment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentStatusChangedEventDispatcherImpl implements PaymentStatusChangedEventDispatcher {

    private final RabbitTemplate rabbitTemplate;

    private final String paymentStatusChangedQueue;

    public PaymentStatusChangedEventDispatcherImpl(RabbitTemplate rabbitTemplate, @Value("${application.queue.paymentStatusChanged.name}") String paymentStatusChangedQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.paymentStatusChangedQueue = paymentStatusChangedQueue;
    }

    @Override
    public void dispatch(Payment payment) {

        PaymentStatusChangedEvent event = PaymentStatusChangedEvent.builder()
                .id(payment.getId())
                .qrCode(payment.getQrCode())
                .status(payment.getStatus())
                .build();

        rabbitTemplate.convertAndSend(paymentStatusChangedQueue, event);
    }

}
