package com.vinisnzy.cinema.mappers;

import com.vinisnzy.cinema.enums.Classification;
import com.vinisnzy.cinema.models.movie.Movie;
import com.vinisnzy.cinema.models.movie.MovieRequestDTO;
import com.vinisnzy.cinema.models.movie.MovieResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieMapper {
    public Movie toEntity(MovieRequestDTO data) {
        Movie movie = new Movie();
        movie.setTitle(data.title());
        movie.setDurationMinutes(data.durationMinutes());
        movie.setGender(data.gender());
        movie.setClassification(data.classification());
        movie.setSessions(List.of());
        return movie;
    }

    public MovieResponseDTO toResponseDTO(Movie movie) {
        return new MovieResponseDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getDurationMinutes(),
                movie.getGender(),
                movie.getClassification().getValue()
        );
    }
}
