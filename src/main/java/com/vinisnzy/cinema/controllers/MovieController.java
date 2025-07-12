package com.vinisnzy.cinema.controllers;

import com.vinisnzy.cinema.config.SecurityConfig;
import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.dtos.movie.MovieRequestDTO;
import com.vinisnzy.cinema.dtos.movie.MovieResponseDTO;
import com.vinisnzy.cinema.services.MovieService;
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
@RequestMapping("api/admin/movies")
@RequiredArgsConstructor
@Tag(name = "Movies", description = "Endpoints for managing movies.")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class MovieController {

    private final MovieService service;

    @Operation(summary = "Get All Movies", description = "Return a paginated list of all registered movies.")
    @GetMapping
    public ResponseEntity<CustomPageDTO<MovieResponseDTO>> getAllMovies(Pageable pageable) {
        return ResponseEntity.ok(service.getAllMovies(pageable));
    }

    @Operation(summary = "Get Movie By ID", description = "Returns the details of a specific movie by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> getMovieById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getMovieById(id));
    }

    @Operation(summary = "Create a New Movie", description = "Registers a new movie using the provided data.")
    @PostMapping
    public ResponseEntity<MovieResponseDTO> createMovie(@RequestBody @Valid MovieRequestDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createMovie(data));
    }

    @Operation(summary = "Update an Existing Movie", description = "Updates the information of an existing movie based on its ID.")
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> updateMovie(@PathVariable UUID id, @Valid @RequestBody MovieRequestDTO data) {
        return ResponseEntity.ok(service.updateMovie(id, data));
    }

    @Operation(summary = "Delete Movie", description = "Delete a movie by its id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable UUID id) {
        service.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
