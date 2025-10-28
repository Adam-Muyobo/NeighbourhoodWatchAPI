package com.neighbourhoodwatch.api.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface ApiTokenRepository extends JpaRepository<ApiToken, Long> {

    Optional<ApiToken> findByToken(String token);

    void deleteByToken(String token);

    // optional cleanup
    void deleteAllByExpiresAtBefore(Instant cutoff);
}
