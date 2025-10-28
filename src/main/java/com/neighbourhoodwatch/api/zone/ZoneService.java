package com.neighbourhoodwatch.api.zone;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;

    // Create Zone
    public Zone createZone(Zone zone) {
        return zoneRepository.save(zone);
    }

    // Get All Zones
    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }


    // Get Zone by UUID
    public Optional<Zone> getZoneByUUID(String uuid) {
        return zoneRepository.findByZoneUUID(uuid);
    }

    // Update Zone
    public Optional<Zone> updateZone(String uuid, Zone updatedZone) {
        return zoneRepository.findByZoneUUID(uuid).map(existing -> {
            existing.setName(updatedZone.getName());
            existing.setDescription(updatedZone.getDescription());
            return zoneRepository.save(existing);
        });
    }

    // Delete Zone
    public boolean deleteZone(String uuid) {
        return zoneRepository.findByZoneUUID(uuid).map(zone -> {
            zoneRepository.delete(zone);
            return true;
        }).orElse(false);
    }
}
