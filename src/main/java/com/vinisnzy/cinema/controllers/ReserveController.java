package com.vinisnzy.cinema.controllers;

import com.vinisnzy.cinema.config.SecurityConfig;
import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.dtos.reserve.ReserveRequestDTO;
import com.vinisnzy.cinema.dtos.reserve.ReserveResponseDTO;
import com.vinisnzy.cinema.services.ReserveService;
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
@RequestMapping("/api/admin/reserves")
@RequiredArgsConstructor
@SecurityRequirement(name = SecurityConfig.SECURITY)
@Tag(name = "Reserves", description = "Endpoints for managing reserves.")
public class ReserveController {
    private final ReserveService service;

    @Operation(summary = "Get All Reserves", description = "Return a paginated list of all registered reserves.")
    @GetMapping
    public ResponseEntity<CustomPageDTO<ReserveResponseDTO>> getAllReserves(Pageable pageable) {
        return ResponseEntity.ok(service.getAllReserves(pageable));
    }

    @Operation(summary = "Get Reserve By ID", description = "Returns the details of a specific reserve by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ReserveResponseDTO> getReserveById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getReserveById(id));
    }

    @Operation(summary = "Create a New Reserve", description = "Registers a new reserve using the provided data.")
    @PostMapping
    public ResponseEntity<ReserveResponseDTO> createReserve(@RequestBody @Valid ReserveRequestDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createReserve(data));
    }

    @Operation(summary = "Update an Existing Reserve", description = "Updates the information of an existing reserve based on its ID.")
    @PutMapping("/{id}")
    public ResponseEntity<ReserveResponseDTO> updateReserve(@PathVariable UUID id, @Valid @RequestBody ReserveRequestDTO data) {
        return ResponseEntity.ok(service.updateReserve(id, data));
    }

    @Operation(summary = "Delete Reserve", description = "Delete a reserve by its id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserve(@PathVariable UUID id) {
        service.deleteReserve(id);
        return ResponseEntity.noContent().build();
    }
}
