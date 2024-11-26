package com.fiap.challenge.payment.app.adapter.output.queue;

import com.fiap.challenge.payment.core.domain.Payment;

public interface PaymentStatusChangedEventDispatcher {

    void dispatch(Payment payment);

}
