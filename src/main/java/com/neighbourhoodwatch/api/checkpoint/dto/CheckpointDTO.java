package com.neighbourhoodwatch.api.checkpoint.dto;

import com.neighbourhoodwatch.api.checkpoint.Checkpoint.CheckpointType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckpointDTO {
    private String checkpointUUID;
    private String code;
    private String name;
    private CheckpointType type;
    private String description;
    private String houseUUID; // nullable
    private String location;
}
