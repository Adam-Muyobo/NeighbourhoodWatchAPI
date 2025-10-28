package com.neighbourhoodwatch.api.audit;

import com.neighbourhoodwatch.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_log", uniqueConstraints = {
        @UniqueConstraint(columnNames = "auditUUID")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    @Column(nullable = false, unique = true, length = 36)
    private String auditUUID;

    /**
     * The user who performed the action (might be null for system events).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", foreignKey = @ForeignKey(name = "fk_audit_actor"))
    private User actor;

    /**
     * Action type e.g. "CREATE_USER", "SUSPEND_OFFICER", "PATROL_ANOMALY"
     */
    @Column(nullable = false, length = 100)
    private String actionType;

    /**
     * Optional: the type of the target (USER, HOUSE, CHECKPOINT, PATROL, SOS, etc.)
     */
    @Column(length = 100)
    private String targetType;

    /**
     * Optional: the numeric id of the target (if applicable)
     */
    @Column
    private Long targetId;

    /**
     * Free-form details (JSON string recommended) describing the action.
     */
    @Column(columnDefinition = "TEXT")
    private String details;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.auditUUID == null) {
            this.auditUUID = UUID.randomUUID().toString();
        }
    }
}
