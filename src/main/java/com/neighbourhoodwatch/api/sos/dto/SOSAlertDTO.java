package com.neighbourhoodwatch.api.sos.dto;

import com.neighbourhoodwatch.api.sos.SOSAlert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SOSAlertDTO {

    private String sosUUID;
    private String memberUUID;
    private String checkpointUUID;
    private String status;
    private String description;
    private Instant createdAt;
    private Instant resolvedAt;
    private BigDecimal geoLat;
    private BigDecimal geoLng;

    public static SOSAlertDTO fromEntity(SOSAlert alert) {
        return SOSAlertDTO.builder()
                .sosUUID(alert.getSosUUID())
                .memberUUID(alert.getMember().getUserUUID())
                .checkpointUUID(alert.getCheckpoint() != null ? alert.getCheckpoint().getCheckpointUUID() : null)
                .status(alert.getStatus().name())
                .description(alert.getDescription())
                .createdAt(alert.getCreatedAt())
                .resolvedAt(alert.getResolvedAt())
                .geoLat(alert.getGeoLat())
                .geoLng(alert.getGeoLng())
                .build();
    }
}
