package com.neighbourhoodwatch.api.patrol;

import com.neighbourhoodwatch.api.checkpoint.Checkpoint;
import com.neighbourhoodwatch.api.checkpoint.CheckpointRepository;
import com.neighbourhoodwatch.api.patrol.dto.PatrolDTO;
import com.neighbourhoodwatch.api.user.User;
import com.neighbourhoodwatch.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PatrolService {

    private final PatrolRepository patrolRepository;
    private final UserRepository userRepository;
    private final CheckpointRepository checkpointRepository;

    public PatrolDTO createPatrol(String officerUUID, String checkpointUUID, String comment,
                                  Boolean anomalyFlag, Double latitude, Double longitude) {
        User officer = userRepository.findByUserUUID(officerUUID)
                .orElseThrow(() -> new RuntimeException("Officer not found"));
        Checkpoint checkpoint = checkpointRepository.findByCheckpointUUID(checkpointUUID)
                .orElseThrow(() -> new RuntimeException("Checkpoint not found"));

        Patrol patrol = Patrol.builder()
                .officer(officer)
                .checkpoint(checkpoint)
                .comment(comment)
                .anomalyFlag(anomalyFlag != null ? anomalyFlag : false)
                .latitude(latitude != null ? BigDecimal.valueOf(latitude) : null)
                .longitude(longitude != null ? BigDecimal.valueOf(longitude) : null)
                .build();

        Patrol savedPatrol = patrolRepository.save(patrol);
        return PatrolDTO.fromEntity(savedPatrol);
    }

    @Transactional(readOnly = true)
    public List<PatrolDTO> getAllPatrols() {
        return patrolRepository.findAll().stream()
                .map(PatrolDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PatrolDTO getPatrolByUUID(String uuid) {
        Patrol patrol = patrolRepository.findByPatrolUUID(uuid)
                .orElseThrow(() -> new RuntimeException("Patrol not found"));
        return PatrolDTO.fromEntity(patrol);
    }

    public PatrolDTO updatePatrolComment(String patrolUUID, String comment, Boolean anomalyFlag) {
        Patrol patrol = patrolRepository.findByPatrolUUID(patrolUUID)
                .orElseThrow(() -> new RuntimeException("Patrol not found"));

        if (comment != null) patrol.setComment(comment);
        if (anomalyFlag != null) patrol.setAnomalyFlag(anomalyFlag);

        Patrol updatedPatrol = patrolRepository.save(patrol);
        return PatrolDTO.fromEntity(updatedPatrol);
    }

    public void deletePatrol(String patrolUUID) {
        Patrol patrol = patrolRepository.findByPatrolUUID(patrolUUID)
                .orElseThrow(() -> new RuntimeException("Patrol not found"));
        patrolRepository.delete(patrol);
    }
}