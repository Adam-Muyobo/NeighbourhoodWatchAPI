package com.neighbourhoodwatch.api.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    Optional<PaymentTransaction> findByPaymentUUID(String uuid);
    Optional<PaymentTransaction> findByReference(String reference);
}
