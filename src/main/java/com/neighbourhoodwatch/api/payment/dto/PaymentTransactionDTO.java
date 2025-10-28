package com.neighbourhoodwatch.api.payment.dto;

import com.neighbourhoodwatch.api.payment.PaymentTransaction;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PaymentTransactionDTO {
    private String paymentUUID;
    private BigDecimal amount;
    private PaymentTransaction.PaymentMethod paymentMethod;
    private Instant paidAt;
    private String reference;
    private SubscriptionDTO subscription;

    public static PaymentTransactionDTO fromEntity(PaymentTransaction entity) {
        PaymentTransactionDTO dto = new PaymentTransactionDTO();
        dto.setPaymentUUID(entity.getPaymentUUID());
        dto.setAmount(entity.getAmount());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setPaidAt(entity.getPaidAt());
        dto.setReference(entity.getReference());
        dto.setSubscription(SubscriptionDTO.fromEntity(entity.getSubscription()));
        return dto;
    }
}