package com.neighbourhoodwatch.api.patrol;

import com.neighbourhoodwatch.api.checkpoint.Checkpoint;
import com.neighbourhoodwatch.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "patrol", uniqueConstraints = {
        @UniqueConstraint(columnNames = "patrolUUID")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patrol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patrolId;

    @Column(nullable = false, unique = true, length = 36)
    private String patrolUUID;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "officer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_patrol_officer"))
    private User officer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "checkpoint_id", nullable = false, foreignKey = @ForeignKey(name = "fk_patrol_checkpoint"))
    private Checkpoint checkpoint;

    @Column
    private Instant scannedAt;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal latitude;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false)
    private Boolean anomalyFlag = Boolean.FALSE;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.patrolUUID == null) {
            this.patrolUUID = UUID.randomUUID().toString();
        }
        if (this.scannedAt == null) {
            this.scannedAt = Instant.now();
        }
    }
}
