package com.neighbourhoodwatch.api.payment;

import com.neighbourhoodwatch.api.subscription.Subscription;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment_transaction", uniqueConstraints = {
        @UniqueConstraint(columnNames = "paymentUUID"),
        @UniqueConstraint(columnNames = "reference")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction {

    public enum PaymentMethod { MOBILE_MONEY, CARD, CASH }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false, unique = true, length = 36)
    private String paymentUUID;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", nullable = false, foreignKey = @ForeignKey(name = "fk_payment_subscription"))
    private Subscription subscription;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private PaymentMethod paymentMethod;

    @CreationTimestamp
    private Instant paidAt;

    @Column(length = 255, unique = true)
    private String reference;

    @PrePersist
    public void prePersist() {
        if (this.paymentUUID == null) {
            this.paymentUUID = UUID.randomUUID().toString();
        }
    }
}
