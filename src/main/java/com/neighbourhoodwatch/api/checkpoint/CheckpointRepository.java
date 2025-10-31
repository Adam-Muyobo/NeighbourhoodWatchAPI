package com.neighbourhoodwatch.api.checkpoint;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {
    Optional<Checkpoint> findByCheckpointUUID(String uuid);
    Optional<Checkpoint> findByCode(String code);
}
