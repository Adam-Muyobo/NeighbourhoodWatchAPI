package com.neighbourhoodwatch.api.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Anyone can GET
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Admin-only create
    @PostMapping("/admin/{adminId}/create")
    public ResponseEntity<?> createUser(@PathVariable Long adminId,
                                        @Valid @RequestBody CreateUserRequest req) {

        if (!isAdmin(adminId)) {
            return forbidden();
        }

        User created = userService.createUser(req);
        return ResponseEntity.ok(created);
    }

    // ================= Admin Management Actions ================= //

    @PostMapping("/{adminId}/approve/{id}")
    public ResponseEntity<?> approveUser(@PathVariable Long adminId, @PathVariable Long id) {
        if (!isAdmin(adminId)) return forbidden();

        return userService.approveUser(id)
                .map(u -> ResponseEntity.ok(status("approved", u.getUserId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{adminId}/reject/{id}")
    public ResponseEntity<?> rejectUser(@PathVariable Long adminId, @PathVariable Long id) {
        if (!isAdmin(adminId)) return forbidden();

        return userService.rejectUser(id)
                .map(u -> ResponseEntity.ok(status("rejected", u.getUserId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{adminId}/suspend/{id}")
    public ResponseEntity<?> suspendUser(@PathVariable Long adminId, @PathVariable Long id) {
        if (!isAdmin(adminId)) return forbidden();

        return userService.suspendUser(id)
                .map(u -> ResponseEntity.ok(status("suspended", u.getUserId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{adminId}/block/{id}")
    public ResponseEntity<?> blockUser(@PathVariable Long adminId, @PathVariable Long id) {
        if (!isAdmin(adminId)) return forbidden();

        return userService.blockUser(id)
                .map(u -> ResponseEntity.ok(status("blocked", u.getUserId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{adminId}/reinstate/{id}")
    public ResponseEntity<?> reinstateUser(@PathVariable Long adminId, @PathVariable Long id) {
        if (!isAdmin(adminId)) return forbidden();

        return userService.reinstateUser(id)
                .map(u -> ResponseEntity.ok(status("reinstated", u.getUserId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{adminId}/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long adminId, @PathVariable Long id) {
        if (!isAdmin(adminId)) return forbidden();

        userService.deleteUser(id);
        return ResponseEntity.ok(status("deleted", id));
    }


    // ================= Helpers ================= //

    private final Map<String, Object> forbiddenMsg = Map.of("error", "admin_required");

    private ResponseEntity<?> forbidden() {
        return ResponseEntity.status(403).body(forbiddenMsg);
    }

    private Map<String, Object> status(String status, Long id) {
        return Map.of("status", status, "userId", id);
    }

    private boolean isAdmin(Long adminId) {
        User u = userService.getUserById(adminId);
        return u != null && u.getRole() == User.Role.ADMIN;
    }
}
