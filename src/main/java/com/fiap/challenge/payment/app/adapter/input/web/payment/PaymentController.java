package com.fiap.challenge.payment.app.adapter.input.web.payment;

import com.fiap.challenge.payment.app.adapter.input.web.payment.dto.OrderRequest;
import com.fiap.challenge.payment.app.adapter.input.web.payment.dto.PaymentResponse;
import com.fiap.challenge.payment.app.adapter.input.web.payment.mapper.OrderRequestMapper;
import com.fiap.challenge.payment.app.adapter.input.web.payment.mapper.PaymentResponseMapper;
import com.fiap.challenge.payment.core.domain.Order;
import com.fiap.challenge.payment.core.domain.Payment;
import com.fiap.challenge.payment.core.usecases.payment.PaymentCreateUseCase;
import com.fiap.challenge.payment.core.usecases.payment.PaymentGetUseCase;
import com.fiap.challenge.payment.core.usecases.payment.PaymentVerifyUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
public class PaymentController {

    private final PaymentCreateUseCase paymentCreateUseCase;
    private final PaymentGetUseCase paymentGetUseCase;
    private final PaymentVerifyUseCase paymentVerifyUseCase;
    private final OrderRequestMapper orderRequestMapper;
    private final PaymentResponseMapper paymentResponseMapper;

    @PostMapping
    public PaymentResponse create(@RequestBody OrderRequest orderRequest) {
        Order order = orderRequestMapper.toOrder(orderRequest);
        Payment payment = paymentCreateUseCase.execute(order);
        return paymentResponseMapper.toPaymentResponse(payment);
    }

    @GetMapping("/{id}")
    public PaymentResponse get(@PathVariable Long id) {
        Payment payment = paymentGetUseCase.execute(id);
        return paymentResponseMapper.toPaymentResponse(payment);
    }

    @PostMapping(path = "/{uuid}/callback")
    public void callback(@PathVariable UUID uuid, @RequestParam(required = false) String id, @RequestParam String topic) {
        log.info("Callback received for payment {}: externalId={} topic={}", uuid, id, topic);
        if (List.of("merchant_order", "payment").contains(topic)) {
            paymentVerifyUseCase.execute(uuid, id);
        }
    }

}
