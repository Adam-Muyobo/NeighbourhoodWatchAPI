package com.neighbourhoodwatch.api.house;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "house", uniqueConstraints = {
        @UniqueConstraint(columnNames = "houseUUID")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long houseId;

    @Column(nullable = false, unique = true, length = 36)
    private String houseUUID;

    @Column(nullable = false, length = 100)
    private String nameOrNumber;

    @Column(length = 255)
    private String location;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.houseUUID == null) {
            this.houseUUID = UUID.randomUUID().toString();
        }
    }
}
