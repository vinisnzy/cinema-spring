package com.vinisnzy.cinema.controllers;

import com.vinisnzy.cinema.config.SecurityConfig;
import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.dtos.seat.SeatRequestDTO;
import com.vinisnzy.cinema.dtos.seat.SeatResponseDTO;
import com.vinisnzy.cinema.services.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Tag(name = "Seats", description = "Endpoints for managing seats.")
public class SeatController {

    private final SeatService service;

    @Operation(summary = "Get All Seats By Session", description = "Return a paginated list of all registered seats by session.")
    @GetMapping("/seats/session/{id}")
    public ResponseEntity<CustomPageDTO<SeatResponseDTO>> getAllSeatsBySessionId(
            @PathVariable UUID id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAllSeatsBySessionId(id, pageable));
    }

    @Operation(summary = "Get Seat By ID", description = "Returns the details of a specific seat by its ID.")
    @GetMapping("/seats/{id}")
    public ResponseEntity<SeatResponseDTO> getSeatById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getSeatById(id));
    }

    @Operation(summary = "Get All Seats By Reserve", description = "Return a paginated list of all registered seats by reserve.")
    @GetMapping("/seats/reserve/{id}")
    public ResponseEntity<CustomPageDTO<SeatResponseDTO>> getSeatsByReserveId(
            @PathVariable UUID id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAllSeatsByReserveId(id, pageable));
    }

    @Operation(summary = "Get All Reserved Seats By Session", description = "Return a paginated list of all reserved seats by session.")
    @GetMapping("/seats/session/{id}/reserved")
    public ResponseEntity<CustomPageDTO<SeatResponseDTO>> getReservedSeatsBySessionId(
            @PathVariable UUID id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAllReservedSeatsBySessionId(id, pageable));
    }

    @Operation(summary = "Get All Available Seats By Session", description = "Return a paginated list of all available seats by session.")
    @GetMapping("/seats/session/{id}/available")
    public ResponseEntity<CustomPageDTO<SeatResponseDTO>> getAvailableSeatsBySessionId(
            @PathVariable UUID id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAllAvailableSeatsBySessionId(id, pageable));
    }

    @Operation(summary = "Create a New Seat",
            description = "Registers a new seat using the provided data.",
            security = @SecurityRequirement(name = SecurityConfig.SECURITY))
    @PostMapping("/admin/seats")
    public ResponseEntity<SeatResponseDTO> createSeat(@RequestBody @Valid SeatRequestDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createSeat(data));
    }

    @Operation(summary = "Update an Existing Seat",
            description = "Updates the information of an existing seat based on its ID.",
            security = @SecurityRequirement(name = SecurityConfig.SECURITY))
    @PutMapping("/admin/seats/{id}")
    public ResponseEntity<SeatResponseDTO> updateSeat(@PathVariable UUID id, @Valid @RequestBody SeatRequestDTO data) {
        return ResponseEntity.ok(service.updateSeat(id, data));
    }

    @Operation(summary = "Delete Seat",
            description = "Delete a seat by its id.",
            security = @SecurityRequirement(name = SecurityConfig.SECURITY))
    @DeleteMapping("/admin/seats/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable UUID id) {
        service.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }
}
