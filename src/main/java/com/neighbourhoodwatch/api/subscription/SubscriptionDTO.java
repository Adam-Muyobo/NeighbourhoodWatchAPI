package com.neighbourhoodwatch.api.subscription;

import lombok.*;
import java.time.LocalDate;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {
    private String subscriptionUUID;
    private String memberUUID; // userUUID
    private String status;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Instant createdAt;
    private Instant updatedAt;
}
