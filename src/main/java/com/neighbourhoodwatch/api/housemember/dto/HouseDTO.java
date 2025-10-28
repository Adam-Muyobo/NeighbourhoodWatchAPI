package com.neighbourhoodwatch.api.housemember.dto;

import com.neighbourhoodwatch.api.house.House;
import lombok.Data;
import java.time.Instant;

@Data
public class HouseDTO {
    private String houseUUID;
    private String nameOrNumber;
    private String location;
    private Instant createdAt;

    public static HouseDTO fromEntity(House house) {
        HouseDTO dto = new HouseDTO();
        dto.setHouseUUID(house.getHouseUUID());
        dto.setNameOrNumber(house.getNameOrNumber());
        dto.setLocation(house.getLocation());
        dto.setCreatedAt(house.getCreatedAt());
        return dto;
    }
}