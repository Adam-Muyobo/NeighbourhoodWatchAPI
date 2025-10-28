package com.neighbourhoodwatch.api.subscription;

import com.neighbourhoodwatch.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "subscription", uniqueConstraints = {
        @UniqueConstraint(columnNames = "subscriptionUUID")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    public enum SubscriptionStatus { ACTIVE, IN_ARREARS, SUSPENDED }

    public enum SubscriptionType {Monthly, Annually}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    @Column(nullable = false, unique = true, length = 36)
    private String subscriptionUUID;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(name = "fk_subscription_member"))
    private User member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SubscriptionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SubscriptionType type;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.subscriptionUUID == null) {
            this.subscriptionUUID = UUID.randomUUID().toString();
        }
    }
}
