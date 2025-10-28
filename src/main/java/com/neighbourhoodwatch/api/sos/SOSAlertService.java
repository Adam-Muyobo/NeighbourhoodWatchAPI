package com.neighbourhoodwatch.api.sos;

import com.neighbourhoodwatch.api.checkpoint.Checkpoint;
import com.neighbourhoodwatch.api.checkpoint.CheckpointRepository;
import com.neighbourhoodwatch.api.sos.dto.SOSAlertDTO;
import com.neighbourhoodwatch.api.user.User;
import com.neighbourhoodwatch.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SOSAlertService {

    private final SOSAlertRepository sosAlertRepository;
    private final UserRepository userRepository;
    private final CheckpointRepository checkpointRepository;

    public SOSAlertDTO createSOSAlert(String memberUUID, String description,
                                      BigDecimal geoLat, BigDecimal geoLng,
                                      String checkpointUUID) {
        User member = userRepository.findByUserUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Checkpoint checkpoint = null;
        if (checkpointUUID != null) {
            checkpoint = checkpointRepository.findByCheckpointUUID(checkpointUUID)
                    .orElseThrow(() -> new RuntimeException("Checkpoint not found"));
        }

        SOSAlert alert = SOSAlert.builder()
                .member(member)
                .checkpoint(checkpoint)
                .description(description)
                .geoLat(geoLat)
                .geoLng(geoLng)
                .status(SOSAlert.SOSStatus.OPEN)
                .build();

        return SOSAlertDTO.fromEntity(sosAlertRepository.save(alert));
    }

    public List<SOSAlertDTO> getAllSOSAlerts() {
        return sosAlertRepository.findAll()
                .stream()
                .map(SOSAlertDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public SOSAlertDTO getSOSAlertByUUID(String sosUUID) {
        SOSAlert alert = sosAlertRepository.findBySosUUID(sosUUID)
                .orElseThrow(() -> new RuntimeException("SOS Alert not found"));
        return SOSAlertDTO.fromEntity(alert);
    }

    public SOSAlertDTO updateSOSAlertStatus(String sosUUID, SOSAlert.SOSStatus status) {
        SOSAlert alert = sosAlertRepository.findBySosUUID(sosUUID)
                .orElseThrow(() -> new RuntimeException("SOS Alert not found"));
        alert.setStatus(status);
        if (status == SOSAlert.SOSStatus.RESOLVED) {
            alert.setResolvedAt(Instant.now());
        }
        return SOSAlertDTO.fromEntity(alert);
    }

    public void deleteSOSAlert(String sosUUID) {
        SOSAlert alert = sosAlertRepository.findBySosUUID(sosUUID)
                .orElseThrow(() -> new RuntimeException("SOS Alert not found"));
        sosAlertRepository.delete(alert);
    }
}
