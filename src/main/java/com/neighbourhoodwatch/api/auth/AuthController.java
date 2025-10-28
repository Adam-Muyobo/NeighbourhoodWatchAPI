package com.neighbourhoodwatch.api.auth;

import com.neighbourhoodwatch.api.user.User;
import com.neighbourhoodwatch.api.user.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    // ========= Registration DTO ========= //
    @Data
    public static class RegisterRequest {
        private String name;
        private String email;
        private String phoneNumber;
        private String password; // raw password
    }

    // ========= Login DTO ================//
    @Data
    public static class LoginRequest {
        private String identifier;
        private String password; // raw password for server-side hashing
    }

    // ========= Registration Endpoints ========= //

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest req) {
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPhoneNumber(req.getPhoneNumber());

        authService.registerAdmin(user, req.getPassword());

        return ResponseEntity.ok("Admin registered successfully");
    }

    @PostMapping("/register/officer")
    public ResponseEntity<?> registerOfficer(@RequestBody RegisterRequest req) {
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPhoneNumber(req.getPhoneNumber());

        authService.registerOfficer(user, req.getPassword());

        return ResponseEntity.ok("Officer registered. Pending approval by Admin.");
    }

    @PostMapping("/register/member")
    public ResponseEntity<?> registerMember(@RequestBody RegisterRequest req) {
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPhoneNumber(req.getPhoneNumber());

        authService.registerMember(user, req.getPassword());

        return ResponseEntity.ok("Member registered. Pending approval by Admin.");
    }


    // ========= Login Endpoint ========= //

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = authService.login(request.getIdentifier(), request.getPassword());
        if (user == null) {
            return ResponseEntity.status(401)
                    .body("Invalid credentials or inactive account.");
        }

        return ResponseEntity.ok(user);
    }

    // ========= Utility Endpoint (optional) ========= //

    @GetMapping("/check/{identifier}")
    public ResponseEntity<?> checkUser(@PathVariable String identifier) {
        return ResponseEntity.ok(
                userRepository.findByEmailOrPhoneNumber(identifier, identifier)
                        .orElse(null)
        );
    }
}
