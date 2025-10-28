package com.neighbourhoodwatch.api.sos;

import com.neighbourhoodwatch.api.checkpoint.Checkpoint;
import com.neighbourhoodwatch.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sos_alert", uniqueConstraints = {
        @UniqueConstraint(columnNames = "sosUUID")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SOSAlert {

    public enum SOSStatus { OPEN, IN_PROGRESS, RESOLVED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sosId;

    @Column(nullable = false, unique = true, length = 36)
    private String sosUUID;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sos_member"))
    private User member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checkpoint_id", foreignKey = @ForeignKey(name = "fk_sos_checkpoint"))
    private Checkpoint checkpoint; // nullable

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SOSStatus status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    private Instant createdAt;

    private Instant resolvedAt;

    @Column(precision = 11, scale = 8)
    private BigDecimal geoLat;

    @Column(precision = 11, scale = 8)
    private BigDecimal geoLng;

    @PrePersist
    public void prePersist() {
        if (this.sosUUID == null) {
            this.sosUUID = UUID.randomUUID().toString();
        }
        if (this.status == null) {
            this.status = SOSStatus.OPEN;
        }
    }
}
