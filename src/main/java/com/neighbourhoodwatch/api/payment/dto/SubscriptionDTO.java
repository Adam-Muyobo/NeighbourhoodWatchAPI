package com.neighbourhoodwatch.api.payment.dto;

import com.neighbourhoodwatch.api.subscription.Subscription;
import lombok.Data;
import java.time.Instant;
import java.time.LocalDate;

@Data
public class SubscriptionDTO {
    private String subscriptionUUID;
    private LocalDate startDate;
    private LocalDate endDate;
    private Subscription.SubscriptionStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private MemberDTO member;

    public static SubscriptionDTO fromEntity(Subscription subscription) {
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setSubscriptionUUID(subscription.getSubscriptionUUID());
        dto.setStartDate(subscription.getStartDate());
        dto.setEndDate(subscription.getEndDate());
        dto.setStatus(subscription.getStatus());
        dto.setCreatedAt(subscription.getCreatedAt());
        dto.setUpdatedAt(subscription.getUpdatedAt());
        dto.setMember(MemberDTO.fromEntity(subscription.getMember()));
        return dto;
    }
}