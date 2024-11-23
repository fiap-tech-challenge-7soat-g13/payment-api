package com.fiap.challenge.payment.app.adapter.input.web.payment.dto;

import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {

    private Long id;
    private String qrCode;
    private PaymentStatus status;

}
