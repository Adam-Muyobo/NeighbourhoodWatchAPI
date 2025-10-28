package com.neighbourhoodwatch.api.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Business logic for User management.
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public User getUserById(Long id) { return userRepository.findById(id).orElse(null); }

    public User saveUser(User user) { return userRepository.save(user); }

    public void deleteUser(Long id) { userRepository.deleteById(id); }

    // Admin actions:
    @Transactional
    public Optional<User> approveUser(Long userId) { return setStatus(userId, User.Status.ACTIVE); }

    @Transactional
    public Optional<User> rejectUser(Long userId) { return setStatus(userId, User.Status.INACTIVE); }

    @Transactional
    public Optional<User> suspendUser(Long userId) { return setStatus(userId, User.Status.SUSPENDED); }

    @Transactional
    public Optional<User> blockUser(Long userId) { return setStatus(userId, User.Status.BLOCKED); }

    @Transactional
    public Optional<User> reinstateUser(Long userId) { return setStatus(userId, User.Status.ACTIVE); }

    private Optional<User> setStatus(Long userId, User.Status status) {
        Optional<User> u = userRepository.findById(userId);
        u.ifPresent(user -> {
            user.setStatus(status);
            userRepository.save(user);
        });
        return u;
    }

    /**
     * Create a new user (admin-initiated). Hashes the password before saving.
     * Returns the created user entity.
     */
    @Transactional
    public User createUser(CreateUserRequest req) {
        // Basic conversion from DTO to entity
        User user = User.builder()
                .name(req.getName())
                .phoneNumber(req.getPhoneNumber())
                .email(req.getEmail())
                // For now, we store a hashed password — replace with real hasher below
                .passwordHash(hashPassword(req.getPassword()))
                .role(req.getRole() != null ? req.getRole() : User.Role.MEMBER)
                .status(req.getStatus() != null ? req.getStatus() : User.Status.ACTIVE)
                .build();

        return userRepository.save(user);
    }

    /**
     * Replace this with a proper BCrypt/Argon2 implementation for production.
     * We include a simple placeholder so the code compiles.
     */
    private String hashPassword(String rawPassword) {
        // TODO: swap for BCrypt: new BCryptPasswordEncoder().encode(rawPassword)
        // For development/testing we can keep plain text (not recommended).
        return rawPassword;
    }
}
