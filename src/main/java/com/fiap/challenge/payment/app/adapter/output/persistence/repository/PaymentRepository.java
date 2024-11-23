package com.fiap.challenge.payment.app.adapter.output.persistence.repository;

import com.fiap.challenge.payment.app.adapter.output.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findByUuid(UUID uuid);

}
