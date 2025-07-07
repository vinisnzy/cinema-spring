package com.vinisnzy.cinema.controllers;

import com.vinisnzy.cinema.dtos.seat.SeatRequestDTO;
import com.vinisnzy.cinema.dtos.seat.SeatResponseDTO;
import com.vinisnzy.cinema.services.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService service;

    @GetMapping("/session/{id}")
    public ResponseEntity<List<SeatResponseDTO>> getAllSeatsBySessionId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getAllSeatsBySessionId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatResponseDTO> getSeatById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getSeatById(id));
    }

    @GetMapping("reserve/{id}")
    public ResponseEntity<List<SeatResponseDTO>> getSeatsByReserveId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getSeatsByReserveId(id));
    }

    @GetMapping("/session/{id}/reserved")
    public ResponseEntity<List<SeatResponseDTO>> getReservedSeatsBySessionId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getReservedSeatsBySessionId(id));
    }

    @GetMapping("/session/{id}/available")
    public ResponseEntity<List<SeatResponseDTO>> getAvailableSeatsBySessionId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getAvailableSeatsBySessionId(id));
    }

    @PostMapping
    public ResponseEntity<SeatResponseDTO> createSeat(@RequestBody SeatRequestDTO data) {
        return ResponseEntity.ok(service.createSeat(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatResponseDTO> updateSeat(@PathVariable UUID id, @RequestBody SeatRequestDTO data) {
        return ResponseEntity.ok(service.updateSeat(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable UUID id) {
        service.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }
}
