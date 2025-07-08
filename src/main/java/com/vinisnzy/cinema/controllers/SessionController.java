package com.vinisnzy.cinema.controllers;

import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.dtos.session.SessionRequestDTO;
import com.vinisnzy.cinema.dtos.session.SessionResponseDTO;
import com.vinisnzy.cinema.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService service;

    @GetMapping
    public ResponseEntity<CustomPageDTO<SessionResponseDTO>> getAllSessions(Pageable pageable) {
        return ResponseEntity.ok(service.getAllSessions(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> getSessionById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getSessionById(id));
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<CustomPageDTO<SessionResponseDTO>> getSessionsByMovieId(
            @PathVariable UUID id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getSessionsByMovieId(id, pageable));
    }

    @GetMapping("/room/{room}")
    public ResponseEntity<CustomPageDTO<SessionResponseDTO>> getSessionsByRoom(
            @PathVariable String room,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getSessionsByRoom(room, pageable));
    }

    @PostMapping
    public ResponseEntity<SessionResponseDTO> createSession(@RequestBody SessionRequestDTO data) {
        return ResponseEntity.ok(service.createSession(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> updateSession(@PathVariable UUID id, @RequestBody SessionRequestDTO data) {
        return ResponseEntity.ok(service.updateSession(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable UUID id) {
        service.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
