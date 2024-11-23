package com.fiap.challenge.payment.app.adapter.output.persistence.mapper;

import com.fiap.challenge.payment.app.adapter.output.persistence.entity.PaymentEntity;
import com.fiap.challenge.payment.core.domain.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toPayment(PaymentEntity paymentEntity);

    PaymentEntity toPaymentEntity(Payment payment);

}
