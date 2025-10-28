package com.neighbourhoodwatch.api.auth;

import com.neighbourhoodwatch.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "api_token", indexes = {
        @Index(columnList = "token"),
        @Index(columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // token value returned to client
    @Column(nullable = false, unique = true, length = 36)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_token_user"))
    private User user;

    // expiry (nullable for long-lived tokens)
    private Instant expiresAt;

    @CreationTimestamp
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (this.token == null) {
            this.token = UUID.randomUUID().toString();
        }
    }
}
