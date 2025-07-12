package com.vinisnzy.cinema.controllers;

import com.vinisnzy.cinema.config.SecurityConfig;
import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.dtos.session.SessionRequestDTO;
import com.vinisnzy.cinema.dtos.session.SessionResponseDTO;
import com.vinisnzy.cinema.services.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Tag(name = "Sessions", description = "Endpoints for managing sessions.")
public class SessionController {
    private final SessionService service;

    @Operation(summary = "Get All Sessions", description = "Return a paginated list of all registered sessions.")
    @GetMapping("/sessions")
    public ResponseEntity<CustomPageDTO<SessionResponseDTO>> getAllSessions(Pageable pageable) {
        return ResponseEntity.ok(service.getAllSessions(pageable));
    }

    @Operation(summary = "Get Session By ID", description = "Returns the details of a specific session by its ID.")
    @GetMapping("/sessions/{id}")
    public ResponseEntity<SessionResponseDTO> getSessionById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getSessionById(id));
    }

    @Operation(summary = "Get All Sessions By Movie", description = "Return a paginated list of all registered sessions by movie ID.")
    @GetMapping("/sessions/movie/{id}")
    public ResponseEntity<CustomPageDTO<SessionResponseDTO>> getSessionsByMovieId(
            @PathVariable UUID id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getSessionsByMovieId(id, pageable));
    }

    @Operation(summary = "Get All Sessions By Room", description = "Return a paginated list of all registered sessions by room.")
    @GetMapping("/sessions/room/{room}")
    public ResponseEntity<CustomPageDTO<SessionResponseDTO>> getSessionsByRoom(
            @PathVariable String room,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getSessionsByRoom(room, pageable));
    }

    @Operation(summary = "Create a New Session",
            description = "Registers a new session using the provided data.",
            security = @SecurityRequirement(name = SecurityConfig.SECURITY))
    @PostMapping("/admin/sessions")
    public ResponseEntity<SessionResponseDTO> createSession(@RequestBody @Valid SessionRequestDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createSession(data));
    }

    @Operation(summary = "Update an Existing Session",
            description = "Updates the information of an existing session based on its ID.",
            security = @SecurityRequirement(name = SecurityConfig.SECURITY))
    @PutMapping("/admin/sessions/{id}")
    public ResponseEntity<SessionResponseDTO> updateSession(@PathVariable UUID id, @Valid @RequestBody SessionRequestDTO data) {
        return ResponseEntity.ok(service.updateSession(id, data));
    }

    @Operation(summary = "Delete Session",
            description = "Delete a session by its id.",
            security = @SecurityRequirement(name = SecurityConfig.SECURITY))
    @DeleteMapping("/admin/sessions/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable UUID id) {
        service.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
