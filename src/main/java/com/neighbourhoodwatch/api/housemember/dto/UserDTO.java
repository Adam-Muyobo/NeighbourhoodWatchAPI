package com.neighbourhoodwatch.api.housemember.dto;

import com.neighbourhoodwatch.api.user.User;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class UserDTO {
    private String userUUID;
    private String name;
    private String email;
    private String phoneNumber;
    private User.Role role;
    private User.Status status;
    private OffsetDateTime createdAt;

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserUUID(user.getUserUUID());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}