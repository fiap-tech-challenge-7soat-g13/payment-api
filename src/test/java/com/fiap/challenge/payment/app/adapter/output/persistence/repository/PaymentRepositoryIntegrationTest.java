package com.fiap.challenge.payment.app.adapter.output.persistence.repository;

import com.fiap.challenge.payment.app.adapter.output.persistence.entity.PaymentEntity;
import com.fiap.challenge.payment.core.domain.enums.PaymentStatus;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PaymentRepositoryIntegrationTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void shouldSave() {

        PaymentEntity payment = createPayment();

        PaymentEntity saved = paymentRepository.save(payment);

        PaymentEntity found = entityManager.find(PaymentEntity.class, saved.getId());

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(payment.getUuid(), found.getUuid());
        assertEquals(payment.getExternalId(), found.getExternalId());
        assertEquals(payment.getQrCode(), found.getQrCode());
        assertEquals(payment.getStatus(), found.getStatus());
    }

    @Test
    @Transactional
    void shouldFindById() {

        PaymentEntity payment = createPayment();

        entityManager.persist(payment);

        PaymentEntity found = paymentRepository.findById(payment.getId()).orElse(null);

        assertNotNull(found);
        assertEquals(payment.getUuid(), found.getUuid());
        assertEquals(payment.getExternalId(), found.getExternalId());
        assertEquals(payment.getQrCode(), found.getQrCode());
        assertEquals(payment.getStatus(), found.getStatus());
    }

    @Test
    @Transactional
    void shouldFindByUuid() {

        PaymentEntity payment = createPayment();

        entityManager.persist(payment);

        PaymentEntity found = paymentRepository.findByUuid(payment.getUuid()).orElse(null);

        assertNotNull(found);
        assertEquals(payment.getUuid(), found.getUuid());
        assertEquals(payment.getExternalId(), found.getExternalId());
        assertEquals(payment.getQrCode(), found.getQrCode());
        assertEquals(payment.getStatus(), found.getStatus());
    }

    private static PaymentEntity createPayment() {
        PaymentEntity payment = new PaymentEntity();
        payment.setUuid(UUID.randomUUID());
        payment.setExternalId("externalId");
        payment.setQrCode("qrCode");
        payment.setStatus(PaymentStatus.PENDENTE);
        return payment;
    }

}