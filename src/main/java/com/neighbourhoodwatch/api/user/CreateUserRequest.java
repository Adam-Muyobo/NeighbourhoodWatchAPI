package com.neighbourhoodwatch.api.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for admin to create a new user. Admin may set role and initial status.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 50)
    private String phoneNumber;

    @Email
    @Size(max = 255)
    private String email;

    /**
     * Plain password — service should hash before saving.
     * You can change to accept already hashed passwords if preferred.
     */
    @NotBlank
    private String password;

    /**
     * Role to assign. Admin may create ADMIN/OFFICER/MEMBER.
     */
    private User.Role role;

    /**
     * Initial status for the new user. If omitted, default is ACTIVE.
     */
    private User.Status status;
}
