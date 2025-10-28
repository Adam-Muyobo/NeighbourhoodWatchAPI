package com.neighbourhoodwatch.api.patrol;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatrolRepository extends JpaRepository<Patrol, Long> {
    Optional<Patrol> findByPatrolUUID(String uuid);
}
