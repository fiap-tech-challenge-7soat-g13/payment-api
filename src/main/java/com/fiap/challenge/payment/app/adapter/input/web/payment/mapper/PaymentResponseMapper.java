package com.fiap.challenge.payment.app.adapter.input.web.payment.mapper;

import com.fiap.challenge.payment.app.adapter.input.web.payment.dto.PaymentResponse;
import com.fiap.challenge.payment.core.domain.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentResponseMapper {

    PaymentResponse toPaymentResponse(Payment payment);

}
