package com.neighbourhoodwatch.api.auth;

import com.neighbourhoodwatch.api.user.User;
import com.neighbourhoodwatch.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public void registerAdmin(User user, String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }

        user.setPasswordHash(hashPassword(rawPassword));
        user.setRole(User.Role.ADMIN);
        user.setStatus(User.Status.ACTIVE);
        user.setUserUUID(String.valueOf(UUID.randomUUID()));
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());

        userRepository.save(user);
    }

    @Transactional
    public void registerOfficer(User user, String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }

        user.setPasswordHash(hashPassword(rawPassword));
        user.setRole(User.Role.OFFICER);
        user.setStatus(User.Status.INACTIVE);
        user.setUserUUID(String.valueOf(UUID.randomUUID()));
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());

        userRepository.save(user);
    }

    @Transactional
    public void registerMember(User user, String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }

        user.setPasswordHash(hashPassword(rawPassword));
        user.setRole(User.Role.MEMBER);
        user.setStatus(User.Status.INACTIVE);
        user.setUserUUID(String.valueOf(UUID.randomUUID()));
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());

        userRepository.save(user);
    }


    // Optional: a simple login that returns a user if valid
    @Transactional
    public User login(String emailOrPhone, String rawPassword) {
        String hashedPassword = hashPassword(rawPassword);
        return userRepository.findByEmailOrPhoneNumber(emailOrPhone, emailOrPhone)
                .filter(user -> user.getPasswordHash().equals(hashedPassword))
                .orElse(null);
    }


    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Password hashing failed", ex);
        }
    }
}
