package com.neighbourhoodwatch.api.audit;

import com.neighbourhoodwatch.api.audit.dto.AuditLogDTO;
import com.neighbourhoodwatch.api.user.User;
import com.neighbourhoodwatch.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    public AuditLogDTO createAuditLog(String actorUUID, String actionType, String targetType,
                                      Long targetId, String details) {
        User actor = null;
        if (actorUUID != null) {
            actor = userRepository.findByUserUUID(actorUUID)
                    .orElseThrow(() -> new RuntimeException("Actor not found"));
        }

        AuditLog log = AuditLog.builder()
                .actor(actor)
                .actionType(actionType)
                .targetType(targetType)
                .targetId(targetId)
                .details(details)
                .build();

        return AuditLogDTO.fromEntity(auditLogRepository.save(log));
    }

    public List<AuditLogDTO> getAllAuditLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(AuditLogDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public AuditLogDTO getAuditLogByUUID(String auditUUID) {
        AuditLog log = auditLogRepository.findByAuditUUID(auditUUID)
                .orElseThrow(() -> new RuntimeException("Audit log not found"));
        return AuditLogDTO.fromEntity(log);
    }

    public void deleteAuditLog(String auditUUID) {
        AuditLog log = auditLogRepository.findByAuditUUID(auditUUID)
                .orElseThrow(() -> new RuntimeException("Audit log not found"));
        auditLogRepository.delete(log);
    }
}
