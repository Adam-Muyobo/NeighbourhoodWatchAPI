package com.neighbourhoodwatch.api.audit.dto;

import com.neighbourhoodwatch.api.audit.AuditLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogDTO {
    private String auditUUID;
    private String actorUUID; // may be null
    private String actionType;
    private String targetType;
    private Long targetId;
    private String details;
    private Instant createdAt;

    public static AuditLogDTO fromEntity(AuditLog log) {
        return AuditLogDTO.builder()
                .auditUUID(log.getAuditUUID())
                .actorUUID(log.getActor() != null ? log.getActor().getUserUUID() : null)
                .actionType(log.getActionType())
                .targetType(log.getTargetType())
                .targetId(log.getTargetId())
                .details(log.getDetails())
                .createdAt(log.getCreatedAt())
                .build();
    }
}
