package com.neighbourhoodwatch.api.zone;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "zone", uniqueConstraints = {
        @UniqueConstraint(columnNames = "zoneUUID")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long zoneId;

    @Column(nullable = false, unique = true, length = 36)
    private String zoneUUID;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.zoneUUID == null) {
            this.zoneUUID = UUID.randomUUID().toString();
        }
    }
}
