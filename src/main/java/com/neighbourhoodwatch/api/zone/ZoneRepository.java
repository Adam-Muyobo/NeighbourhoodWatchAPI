package com.neighbourhoodwatch.api.zone;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Optional<Zone> findByZoneUUID(String uuid);
}
