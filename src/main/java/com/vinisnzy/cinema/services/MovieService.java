package com.vinisnzy.cinema.services;

import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.mappers.MovieMapper;
import com.vinisnzy.cinema.dtos.movie.MovieRequestDTO;
import com.vinisnzy.cinema.models.Movie;
import com.vinisnzy.cinema.dtos.movie.MovieResponseDTO;
import com.vinisnzy.cinema.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    @Autowired
    private final MovieMapper movieMapper;

    public CustomPageDTO<MovieResponseDTO> getAllMovies(Pageable pageable) {
        Page<Movie> page = repository.findAll(pageable);
        List<MovieResponseDTO> dtos = page.getContent().stream()
                .map(movieMapper::toResponseDTO).toList();
        return new CustomPageDTO<>(dtos, page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

    public MovieResponseDTO getMovieById(UUID id) {
        Movie movie = getEntityById(id);
        return movieMapper.toResponseDTO(movie);
    }

    public MovieResponseDTO createMovie(MovieRequestDTO data) {
        Movie movie = movieMapper.toEntity(data);
        return movieMapper.toResponseDTO(repository.save(movie));
    }

    public MovieResponseDTO updateMovie(UUID id, MovieRequestDTO data) {
        Movie movie = getEntityById(id);
        movie.setTitle(data.title());
        movie.setDurationMinutes(data.durationMinutes());
        movie.setGender(data.gender());
        movie.setClassification(data.classification());
        return movieMapper.toResponseDTO(repository.save(movie));
    }

    public void deleteMovie(UUID id) {
        repository.deleteById(id);
    }

    public Movie getEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Movie not found"));
    }

    public MovieResponseDTO toResponseDTO(Movie movie) {
        return movieMapper.toResponseDTO(movie);
    }
}
