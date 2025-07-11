package com.vinisnzy.cinema.controllers;

import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.dtos.reserve.ReserveRequestDTO;
import com.vinisnzy.cinema.dtos.reserve.ReserveResponseDTO;
import com.vinisnzy.cinema.services.ReserveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/reserves")
@RequiredArgsConstructor
public class ReserveController {
    private final ReserveService service;

    @GetMapping
    public ResponseEntity<CustomPageDTO<ReserveResponseDTO>> getAllReserves(Pageable pageable) {
        return ResponseEntity.ok(service.getAllReserves(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReserveResponseDTO> getReserveById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getReserveById(id));
    }

    @PostMapping
    public ResponseEntity<ReserveResponseDTO> createReserve(@RequestBody @Valid ReserveRequestDTO data) {
        return ResponseEntity.ok(service.createReserve(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReserveResponseDTO> updateReserve(@PathVariable UUID id, @Valid @RequestBody ReserveRequestDTO data) {
        return ResponseEntity.ok(service.updateReserve(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserve(@PathVariable UUID id) {
        service.deleteReserve(id);
        return ResponseEntity.noContent().build();
    }
}
