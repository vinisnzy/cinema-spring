package com.vinisnzy.cinema.controllers;

import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.dtos.movie.MovieRequestDTO;
import com.vinisnzy.cinema.dtos.movie.MovieResponseDTO;
import com.vinisnzy.cinema.services.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/admin/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @GetMapping
    public ResponseEntity<CustomPageDTO<MovieResponseDTO>> getAllMovies(Pageable pageable) {
        return ResponseEntity.ok(service.getAllMovies(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> getMovieById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getMovieById(id));
    }

    @PostMapping
    public ResponseEntity<MovieResponseDTO> createMovie(@RequestBody @Valid MovieRequestDTO data) {
        return ResponseEntity.ok(service.createMovie(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> updateMovie(@PathVariable UUID id, @Valid @RequestBody MovieRequestDTO data) {
        return ResponseEntity.ok(service.updateMovie(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable UUID id) {
        service.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
