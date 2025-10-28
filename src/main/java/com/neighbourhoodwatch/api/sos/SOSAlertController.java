package com.neighbourhoodwatch.api.sos;

import com.neighbourhoodwatch.api.sos.dto.SOSAlertDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/sos")
@RequiredArgsConstructor
public class SOSAlertController {

    private final SOSAlertService sosAlertService;

    @PostMapping("/{memberUUID}")
    public ResponseEntity<SOSAlertDTO> createSOSAlert(
            @PathVariable String memberUUID,
            @RequestParam String description,
            @RequestParam BigDecimal geoLat,
            @RequestParam BigDecimal geoLng,
            @RequestParam(required = false) String checkpointUUID
    ) {
        return ResponseEntity.ok(
                sosAlertService.createSOSAlert(memberUUID, description, geoLat, geoLng, checkpointUUID)
        );
    }

    @GetMapping
    public ResponseEntity<List<SOSAlertDTO>> getAllSOSAlerts() {
        return ResponseEntity.ok(sosAlertService.getAllSOSAlerts());
    }

    @GetMapping("/{sosUUID}")
    public ResponseEntity<SOSAlertDTO> getSOSAlertByUUID(@PathVariable String sosUUID) {
        return ResponseEntity.ok(sosAlertService.getSOSAlertByUUID(sosUUID));
    }

    @PutMapping("/{sosUUID}")
    public ResponseEntity<SOSAlertDTO> updateSOSAlertStatus(
            @PathVariable String sosUUID,
            @RequestParam SOSAlert.SOSStatus status
    ) {
        return ResponseEntity.ok(sosAlertService.updateSOSAlertStatus(sosUUID, status));
    }

    @DeleteMapping("/{sosUUID}")
    public ResponseEntity<Void> deleteSOSAlert(@PathVariable String sosUUID) {
        sosAlertService.deleteSOSAlert(sosUUID);
        return ResponseEntity.noContent().build();
    }
}
