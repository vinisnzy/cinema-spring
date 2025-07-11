package com.vinisnzy.cinema.controllers;

import com.vinisnzy.cinema.dtos.login.LoginRequestDTO;
import com.vinisnzy.cinema.dtos.login.LoginResponseDTO;
import com.vinisnzy.cinema.dtos.register.RegisterRequestDTO;
import com.vinisnzy.cinema.services.AuthService;
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
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        LoginResponseDTO responseDTO = service.login(data);
        return ResponseEntity.ok(responseDTO);
    }

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
