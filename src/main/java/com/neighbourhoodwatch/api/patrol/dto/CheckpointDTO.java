package com.neighbourhoodwatch.api.patrol.dto;

import com.neighbourhoodwatch.api.checkpoint.Checkpoint;
import lombok.Data;
import java.time.Instant;

@Data
public class CheckpointDTO {
    private String checkpointUUID;
    private String name;
    private Checkpoint.CheckpointType type;
    private String description;
    private String location;
    private Instant createdAt;
    private HouseDTO house; // Can be null

    public static CheckpointDTO fromEntity(Checkpoint checkpoint) {
        CheckpointDTO dto = new CheckpointDTO();
        dto.setCheckpointUUID(checkpoint.getCheckpointUUID());
        dto.setName(checkpoint.getName());
        dto.setType(checkpoint.getType());
        dto.setDescription(checkpoint.getDescription());
        dto.setLocation(checkpoint.getLocation());
        dto.setCreatedAt(checkpoint.getCreatedAt());

        // Only include house if it exists (nullable relationship)
        if (checkpoint.getHouse() != null) {
            dto.setHouse(HouseDTO.fromEntity(checkpoint.getHouse()));
        }

        return dto;
    }
}