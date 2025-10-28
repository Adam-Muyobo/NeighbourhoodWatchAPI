package com.neighbourhoodwatch.api.payment;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    @EntityGraph(attributePaths = {"subscription", "subscription.member"})
    List<PaymentTransaction> findAll();

    @EntityGraph(attributePaths = {"subscription", "subscription.member"})
    Optional<PaymentTransaction> findByPaymentUUID(String paymentUUID);

}