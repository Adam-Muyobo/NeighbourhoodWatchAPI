package com.neighbourhoodwatch.api.housemember.dto;

import com.neighbourhoodwatch.api.housemember.HouseMember;
import lombok.Data;
import java.time.LocalDate;

@Data
public class HouseMemberDTO {
    private String houseMemberUUID;
    private String relationship;
    private LocalDate startDate;
    private LocalDate endDate;
    private UserDTO user;
    private HouseDTO house;

    public static HouseMemberDTO fromEntity(HouseMember entity) {
        HouseMemberDTO dto = new HouseMemberDTO();
        dto.setHouseMemberUUID(entity.getHouseMemberUUID());
        dto.setRelationship(entity.getRelationship());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setUser(UserDTO.fromEntity(entity.getUser()));
        dto.setHouse(HouseDTO.fromEntity(entity.getHouse()));
        return dto;
    }
}