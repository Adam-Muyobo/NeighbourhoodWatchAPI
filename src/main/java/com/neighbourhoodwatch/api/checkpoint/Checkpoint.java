package com.neighbourhoodwatch.api.checkpoint;

import com.neighbourhoodwatch.api.house.House;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "checkpoint", uniqueConstraints = {
        @UniqueConstraint(columnNames = "checkpointUUID")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Checkpoint {

    public enum CheckpointType { HOUSE, GATE, AREA, OTHER }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkpointId;

    @Column(nullable = false, unique = true, length = 36)
    private String checkpointUUID;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CheckpointType type;

    @Column(length = 255)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", foreignKey = @ForeignKey(name = "fk_checkpoint_house"))
    private House house; // nullable

    @Column(length = 255)
    private String location;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.checkpointUUID == null) {
            this.checkpointUUID = UUID.randomUUID().toString();
        }
    }
}
