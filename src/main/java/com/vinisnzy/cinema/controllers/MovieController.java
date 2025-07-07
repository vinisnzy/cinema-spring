package com.vinisnzy.cinema.controllers;

import com.vinisnzy.cinema.models.movie.Movie;
import com.vinisnzy.cinema.models.movie.MovieRequestDTO;
import com.vinisnzy.cinema.models.movie.MovieResponseDTO;
import com.vinisnzy.cinema.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> getAllMovies() {
        return ResponseEntity.ok(service.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> getMovieById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getMovieById(id));
    }

    @PostMapping
    public ResponseEntity<MovieResponseDTO> createMovie(@RequestBody MovieRequestDTO data) {
        return ResponseEntity.ok(service.createMovie(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> updateMovie(@PathVariable UUID id, @RequestBody MovieRequestDTO data) {
        return ResponseEntity.ok(service.updateMovie(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable UUID id) {
        service.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
