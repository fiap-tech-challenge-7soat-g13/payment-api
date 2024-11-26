package com.fiap.challenge.payment.app.adapter.output.queue;

import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentStatusChangedEvent {

    private Long id;
    private String qrCode;
    private PaymentStatus status;

}
