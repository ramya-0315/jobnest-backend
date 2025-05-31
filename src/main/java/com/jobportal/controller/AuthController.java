package com.jobportal.controller;

import com.jobportal.dto.*;
import com.jobportal.model.User;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.UserService;
import com.jobportal.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(UserService userService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = userService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        // Block users from signing up as ADMIN (case-insensitive)
        if (request.getRole() != null && request.getRole().equalsIgnoreCase("ADMIN")) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You are not allowed to register as ADMIN.");
        }

        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }


    @GetMapping("/oauth2/success")
    public String oauth2Success() {
        return "OAuth2 Login Successful!";
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        boolean result = userService.sendPasswordResetLink(email);

        if (result) {
            return ResponseEntity.ok(Map.of("message", "Reset link sent to your email"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean result = userService.resetPassword(request);

        if (result) {
            return ResponseEntity.ok(Map.of("message", "Password reset successful"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid or expired token"));
        }
    }

    @GetMapping("/admin/user")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }
    @GetMapping("/admin/jobs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<JobResponseDTO>> getAllJobsForAdmin() {
        return ResponseEntity.ok(userService.getAllJobsForAdmin());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/jobs/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id, @RequestBody JobRequestDTO jobRequestDTO) {
        try {
            JobResponseDTO updatedJob = userService.updateJob(id, jobRequestDTO);
            return ResponseEntity.ok(updatedJob);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/jobs/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        try {
            userService.deleteJobById(id);
            return ResponseEntity.ok(Map.of("message", "Job deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }


}

