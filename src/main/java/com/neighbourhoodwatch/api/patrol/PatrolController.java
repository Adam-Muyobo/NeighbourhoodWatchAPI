package com.neighbourhoodwatch.api.patrol;

import com.neighbourhoodwatch.api.patrol.dto.PatrolDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrols")
@RequiredArgsConstructor
public class PatrolController {

    private final PatrolService patrolService;

    @PostMapping("/{officerUUID}/{checkpointUUID}")
    public ResponseEntity<PatrolDTO> createPatrol(
            @PathVariable String officerUUID,
            @PathVariable String checkpointUUID,
            @RequestParam(required = false) String comment,
            @RequestParam(required = false) Boolean anomalyFlag,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude
    ) {
        PatrolDTO patrol = patrolService.createPatrol(officerUUID, checkpointUUID, comment, anomalyFlag, latitude, longitude);
        return ResponseEntity.ok(patrol);
    }

    @GetMapping
    public ResponseEntity<List<PatrolDTO>> getAllPatrols() {
        return ResponseEntity.ok(patrolService.getAllPatrols());
    }

    @GetMapping("/{patrolUUID}")
    public ResponseEntity<PatrolDTO> getPatrolByUUID(@PathVariable String patrolUUID) {
        return ResponseEntity.ok(patrolService.getPatrolByUUID(patrolUUID));
    }

    @PutMapping("/{patrolUUID}")
    public ResponseEntity<PatrolDTO> updatePatrol(
            @PathVariable String patrolUUID,
            @RequestParam(required = false) String comment,
            @RequestParam(required = false) Boolean anomalyFlag
    ) {
        return ResponseEntity.ok(patrolService.updatePatrolComment(patrolUUID, comment, anomalyFlag));
    }

    @DeleteMapping("/{patrolUUID}")
    public ResponseEntity<Void> deletePatrol(@PathVariable String patrolUUID) {
        patrolService.deletePatrol(patrolUUID);
        return ResponseEntity.noContent().build();
    }
}