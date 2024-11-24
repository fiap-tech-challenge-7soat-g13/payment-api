package com.fiap.challenge.payment.app.adapter.output.persistence.entity;

import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;

    private String qrCode;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String externalId;

}
