package com.neighbourhoodwatch.api.audit;

import com.neighbourhoodwatch.api.audit.dto.AuditLogDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditlogs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<AuditLogDTO> createAuditLog(
            @RequestParam(required = false) String actorUUID,
            @RequestParam String actionType,
            @RequestParam(required = false) String targetType,
            @RequestParam(required = false) Long targetId,
            @RequestParam(required = false) String details
    ) {
        return ResponseEntity.ok(
                auditLogService.createAuditLog(actorUUID, actionType, targetType, targetId, details)
        );
    }

    @GetMapping
    public ResponseEntity<List<AuditLogDTO>> getAllAuditLogs() {
        return ResponseEntity.ok(auditLogService.getAllAuditLogs());
    }

    @GetMapping("/{auditUUID}")
    public ResponseEntity<AuditLogDTO> getAuditLogByUUID(@PathVariable String auditUUID) {
        return ResponseEntity.ok(auditLogService.getAuditLogByUUID(auditUUID));
    }

    @DeleteMapping("/{auditUUID}")
    public ResponseEntity<Void> deleteAuditLog(@PathVariable String auditUUID) {
        auditLogService.deleteAuditLog(auditUUID);
        return ResponseEntity.noContent().build();
    }
}
