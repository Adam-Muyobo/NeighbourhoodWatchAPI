package com.neighbourhoodwatch.api.payment;

import com.neighbourhoodwatch.api.payment.dto.PaymentTransactionDTO;
import com.neighbourhoodwatch.api.subscription.Subscription;
import com.neighbourhoodwatch.api.subscription.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;

    public PaymentTransactionDTO createPayment(String subscriptionUUID, BigDecimal amount,
                                               PaymentTransaction.PaymentMethod method, String reference) {
        Subscription subscription = subscriptionRepository.findBySubscriptionUUID(subscriptionUUID)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        PaymentTransaction payment = PaymentTransaction.builder()
                .subscription(subscription)
                .amount(amount)
                .paymentMethod(method)
                .reference(reference)
                .build();

        PaymentTransaction savedPayment = paymentRepository.save(payment);
        return PaymentTransactionDTO.fromEntity(savedPayment);
    }

    @Transactional(readOnly = true)
    public List<PaymentTransactionDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(PaymentTransactionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaymentTransactionDTO getPaymentByUUID(String paymentUUID) {
        PaymentTransaction payment = paymentRepository.findByPaymentUUID(paymentUUID)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return PaymentTransactionDTO.fromEntity(payment);
    }

    public PaymentTransactionDTO updatePayment(String paymentUUID, BigDecimal amount,
                                               PaymentTransaction.PaymentMethod method) {
        PaymentTransaction payment = paymentRepository.findByPaymentUUID(paymentUUID)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (amount != null) payment.setAmount(amount);
        if (method != null) payment.setPaymentMethod(method);

        PaymentTransaction updatedPayment = paymentRepository.save(payment);
        return PaymentTransactionDTO.fromEntity(updatedPayment);
    }

    public void deletePayment(String paymentUUID) {
        PaymentTransaction payment = paymentRepository.findByPaymentUUID(paymentUUID)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        paymentRepository.delete(payment);
    }
}