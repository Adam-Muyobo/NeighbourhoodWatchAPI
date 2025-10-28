package com.neighbourhoodwatch.api.patrol.dto;

import com.neighbourhoodwatch.api.user.User;
import lombok.Data;

@Data
public class OfficerDTO {
    private String userUUID;
    private String name;
    private String email;
    private String phoneNumber;
    private User.Role role;
    private User.Status status;

    public static OfficerDTO fromEntity(User user) {
        OfficerDTO dto = new OfficerDTO();
        dto.setUserUUID(user.getUserUUID());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }
}