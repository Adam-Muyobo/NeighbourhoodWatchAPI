package com.neighbourhoodwatch.api.zone;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    // Admin only: Create a zone
    @PostMapping
    public ResponseEntity<Zone> createZone(@RequestBody Zone zone) {
        Zone saved = zoneService.createZone(zone);
        return ResponseEntity.ok(saved);
    }

    // Admin & Members & Officers: View zones
    @GetMapping
    public ResponseEntity<List<Zone>> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllZones());
    }

    // Anyone authenticated: View a single zone
    @GetMapping("/{uuid}")
    public ResponseEntity<Zone> getZoneByUUID(@PathVariable String uuid) {
        return zoneService.getZoneByUUID(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Admin only: Update a zone
    @PutMapping("/{uuid}")
    public ResponseEntity<Zone> updateZone(@PathVariable String uuid, @RequestBody Zone updatedZone) {
        return zoneService.updateZone(uuid, updatedZone)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Admin only: Delete a zone
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteZone(@PathVariable String uuid) {
        boolean deleted = zoneService.deleteZone(uuid);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
