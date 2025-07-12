package com.vinisnzy.cinema.controllers;

import com.vinisnzy.cinema.config.SecurityConfig;
import com.vinisnzy.cinema.dtos.login.LoginRequestDTO;
import com.vinisnzy.cinema.dtos.login.LoginResponseDTO;
import com.vinisnzy.cinema.dtos.register.RegisterRequestDTO;
import com.vinisnzy.cinema.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authorization", description = "Login and Register")
public class AuthController {

    private final AuthService service;

    @Operation(summary = "User Login", description = "Logs in the user and returns their JWT token for authorization.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        LoginResponseDTO responseDTO = service.login(data);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "User Register", description = "Registers the user and saves it in the database.")
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody @Valid RegisterRequestDTO data) {
        try {
            String response = service.register(data, false);
            return ResponseEntity.ok(Collections.singletonMap("message", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @Operation(summary = "Admin Register",
            description = "Registers the admin and saves it in the database, you need to be an admin to register admins",
            security = @SecurityRequirement(name = SecurityConfig.SECURITY))
    @PostMapping("/admin/register-admin")
    public ResponseEntity<Map<String, String>> registerAdmin(@RequestBody @Valid RegisterRequestDTO data) {
        try {
            String response = service.register(data, true);
            return ResponseEntity.ok(Collections.singletonMap("message", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
