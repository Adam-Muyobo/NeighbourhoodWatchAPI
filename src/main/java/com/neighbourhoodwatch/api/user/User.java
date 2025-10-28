package com.neighbourhoodwatch.api.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime; // <-- Changed from Instant
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "userUUID"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phoneNumber")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    public enum Role { ADMIN, OFFICER, MEMBER }
    public enum Status { ACTIVE, INACTIVE, SUSPENDED, BLOCKED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 36)
    private String userUUID;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 50, unique = true)
    private String phoneNumber;

    @Column(length = 255, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Status status = Status.ACTIVE;

    // ===================== CHANGES =====================
    // Changed type from Instant to OffsetDateTime for proper persistence with PostgreSQL timestamptz
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;
    // ====================================================

    @PrePersist
    public void prePersist() {
        if (this.userUUID == null) {
            this.userUUID = UUID.randomUUID().toString();
        }
        // Optional: ensure passwordHash is hashed before saving (if needed)
    }
}
