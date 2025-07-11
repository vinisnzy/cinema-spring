package com.vinisnzy.cinema.controllers;

import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.dtos.session.SessionRequestDTO;
import com.vinisnzy.cinema.dtos.session.SessionResponseDTO;
import com.vinisnzy.cinema.services.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService service;

    @GetMapping("/sessions")
    public ResponseEntity<CustomPageDTO<SessionResponseDTO>> getAllSessions(Pageable pageable) {
        return ResponseEntity.ok(service.getAllSessions(pageable));
    }

    @GetMapping("/sessions/{id}")
    public ResponseEntity<SessionResponseDTO> getSessionById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getSessionById(id));
    }

    @GetMapping("/sessions/movie/{id}")
    public ResponseEntity<CustomPageDTO<SessionResponseDTO>> getSessionsByMovieId(
            @PathVariable UUID id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getSessionsByMovieId(id, pageable));
    }

    @GetMapping("/sessions/room/{room}")
    public ResponseEntity<CustomPageDTO<SessionResponseDTO>> getSessionsByRoom(
            @PathVariable String room,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getSessionsByRoom(room, pageable));
    }

    @PostMapping("/admin/sessions")
    public ResponseEntity<SessionResponseDTO> createSession(@RequestBody @Valid SessionRequestDTO data) {
        return ResponseEntity.ok(service.createSession(data));
    }

    @PutMapping("/admin/sessions/{id}")
    public ResponseEntity<SessionResponseDTO> updateSession(@PathVariable UUID id, @Valid @RequestBody SessionRequestDTO data) {
        return ResponseEntity.ok(service.updateSession(id, data));
    }

    @DeleteMapping("/admin/sessions/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable UUID id) {
        service.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
