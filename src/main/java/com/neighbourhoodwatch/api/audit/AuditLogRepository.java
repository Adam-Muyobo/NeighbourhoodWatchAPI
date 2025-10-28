package com.neighbourhoodwatch.api.audit;

import com.neighbourhoodwatch.api.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    Optional<AuditLog> findByAuditUUID(String uuid);

    Page<AuditLog> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<AuditLog> findByActionTypeOrderByCreatedAtDesc(String actionType, Pageable pageable);

    Page<AuditLog> findByActorOrderByCreatedAtDesc(User actor, Pageable pageable);

    List<AuditLog> findTop100ByOrderByCreatedAtDesc();
}
