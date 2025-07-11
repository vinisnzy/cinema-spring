package com.vinisnzy.cinema.controllers;

import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.dtos.seat.SeatRequestDTO;
import com.vinisnzy.cinema.dtos.seat.SeatResponseDTO;
import com.vinisnzy.cinema.services.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService service;

    @GetMapping("/seats/session/{id}")
    public ResponseEntity<CustomPageDTO<SeatResponseDTO>> getAllSeatsBySessionId(
            @PathVariable UUID id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAllSeatsBySessionId(id, pageable));
    }

    @GetMapping("/seats/{id}")
    public ResponseEntity<SeatResponseDTO> getSeatById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getSeatById(id));
    }

    @GetMapping("/seats/reserve/{id}")
    public ResponseEntity<CustomPageDTO<SeatResponseDTO>> getSeatsByReserveId(
            @PathVariable UUID id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAllSeatsByReserveId(id, pageable));
    }

    @GetMapping("/seats/session/{id}/reserved")
    public ResponseEntity<CustomPageDTO<SeatResponseDTO>> getReservedSeatsBySessionId(
            @PathVariable UUID id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAllReservedSeatsBySessionId(id, pageable));
    }

    @GetMapping("/seats/session/{id}/available")
    public ResponseEntity<CustomPageDTO<SeatResponseDTO>> getAvailableSeatsBySessionId(
            @PathVariable UUID id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAllAvailableSeatsBySessionId(id, pageable));
    }

    @PostMapping("/admin/seats")
    public ResponseEntity<SeatResponseDTO> createSeat(@RequestBody @Valid SeatRequestDTO data) {
        return ResponseEntity.ok(service.createSeat(data));
    }

    @PutMapping("/admin/seats/{id}")
    public ResponseEntity<SeatResponseDTO> updateSeat(@PathVariable UUID id, @Valid @RequestBody SeatRequestDTO data) {
        return ResponseEntity.ok(service.updateSeat(id, data));
    }

    @DeleteMapping("/admin/seats/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable UUID id) {
        service.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }
}
