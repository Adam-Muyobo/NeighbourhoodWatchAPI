package com.neighbourhoodwatch.api.sos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SOSAlertRepository extends JpaRepository<SOSAlert, Long> {
    Optional<SOSAlert> findBySosUUID(String uuid);
}
