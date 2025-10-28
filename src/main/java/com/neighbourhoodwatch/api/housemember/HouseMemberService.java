package com.neighbourhoodwatch.api.housemember;

import com.neighbourhoodwatch.api.house.House;
import com.neighbourhoodwatch.api.house.HouseRepository;
import com.neighbourhoodwatch.api.housemember.dto.HouseMemberDTO;
import com.neighbourhoodwatch.api.user.User;
import com.neighbourhoodwatch.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HouseMemberService {

    private final HouseMemberRepository houseMemberRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;

    public HouseMemberDTO addMemberToHouse(String userUUID, String houseUUID, String relationship) {
        User user = userRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        House house = houseRepository.findByHouseUUID(houseUUID)
                .orElseThrow(() -> new RuntimeException("House not found"));

        HouseMember member = HouseMember.builder()
                .user(user)
                .house(house)
                .relationship(relationship)
                .startDate(LocalDate.now())
                .build();

        HouseMember savedMember = houseMemberRepository.save(member);
        return HouseMemberDTO.fromEntity(savedMember);
    }

    @Transactional(readOnly = true)
    public List<HouseMemberDTO> getAllMembers() {
        return houseMemberRepository.findAll().stream()
                .map(HouseMemberDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<HouseMemberDTO> getByUUID(String uuid) {
        return houseMemberRepository.findByHouseMemberUUID(uuid)
                .map(HouseMemberDTO::fromEntity);
    }

    public HouseMemberDTO updateRelationship(String memberUUID, String newRelation) {
        return houseMemberRepository.findByHouseMemberUUID(memberUUID)
                .map(existing -> {
                    existing.setRelationship(newRelation);
                    HouseMember updated = houseMemberRepository.save(existing);
                    return HouseMemberDTO.fromEntity(updated);
                })
                .orElseThrow(() -> new RuntimeException("House Member not found"));
    }

    public HouseMemberDTO endMembership(String memberUUID) {
        return houseMemberRepository.findByHouseMemberUUID(memberUUID)
                .map(member -> {
                    member.setEndDate(LocalDate.now());
                    HouseMember updated = houseMemberRepository.save(member);
                    return HouseMemberDTO.fromEntity(updated);
                })
                .orElseThrow(() -> new RuntimeException("House Member not found"));
    }

    public void deleteMembership(String memberUUID) {
        HouseMember member = houseMemberRepository.findByHouseMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("House Member not found"));
        houseMemberRepository.delete(member);
    }
}