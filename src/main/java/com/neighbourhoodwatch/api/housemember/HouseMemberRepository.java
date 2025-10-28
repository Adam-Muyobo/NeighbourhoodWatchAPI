package com.neighbourhoodwatch.api.housemember;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseMemberRepository extends JpaRepository<HouseMember, Long> {
    Optional<HouseMember> findByHouseMemberUUID(String uuid);
}
