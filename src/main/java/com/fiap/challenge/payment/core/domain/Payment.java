package com.fiap.challenge.payment.core.domain;

import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Payment {

    private Long id;
    private UUID uuid;
    private String qrCode;
    private PaymentStatus status;
    private String externalId;

}
