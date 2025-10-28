package com.neighbourhoodwatch.api.patrol.dto;

import com.neighbourhoodwatch.api.patrol.Patrol;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PatrolDTO {
    private String patrolUUID;
    private String comment;
    private Boolean anomalyFlag;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Instant createdAt;
    private Instant updatedAt;
    private OfficerDTO officer;
    private CheckpointDTO checkpoint;

    public static PatrolDTO fromEntity(Patrol entity) {
        PatrolDTO dto = new PatrolDTO();
        dto.setPatrolUUID(entity.getPatrolUUID());
        dto.setComment(entity.getComment());
        dto.setAnomalyFlag(entity.getAnomalyFlag());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setOfficer(OfficerDTO.fromEntity(entity.getOfficer()));
        dto.setCheckpoint(CheckpointDTO.fromEntity(entity.getCheckpoint()));
        return dto;
    }
}