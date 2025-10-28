package com.neighbourhoodwatch.api.payment.dto;

import com.neighbourhoodwatch.api.user.User;
import lombok.Data;

@Data
public class MemberDTO {
    private String userUUID;
    private String name;
    private String email;
    private String phoneNumber;
    private User.Role role;
    private User.Status status;

    public static MemberDTO fromEntity(User user) {
        MemberDTO dto = new MemberDTO();
        dto.setUserUUID(user.getUserUUID());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }
}