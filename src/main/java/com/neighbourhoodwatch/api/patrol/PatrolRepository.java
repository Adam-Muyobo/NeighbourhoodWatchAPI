package com.neighbourhoodwatch.api.patrol;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PatrolRepository extends JpaRepository<Patrol, Long> {

    @EntityGraph(attributePaths = {"officer", "checkpoint", "checkpoint.house"})
    List<Patrol> findAll();

    @EntityGraph(attributePaths = {"officer", "checkpoint", "checkpoint.house"})
    Optional<Patrol> findByPatrolUUID(String patrolUUID);
}