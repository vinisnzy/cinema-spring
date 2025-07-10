package com.vinisnzy.cinema.services;

import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.dtos.movie.MovieRequestDTO;
import com.vinisnzy.cinema.dtos.movie.MovieResponseDTO;
import com.vinisnzy.cinema.enums.Classification;
import com.vinisnzy.cinema.exceptions.ResourceNotFoundException;
import com.vinisnzy.cinema.mappers.MovieMapper;
import com.vinisnzy.cinema.models.Movie;
import com.vinisnzy.cinema.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository repository;

    @Mock
    private MovieMapper mapper;

    @InjectMocks
    private MovieService service;

    private Movie movie;
    private MovieResponseDTO movieResponseDTO;
    private MovieRequestDTO movieRequestDTO;
    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(UUID.randomUUID());
        movie.setTitle("Inception");
        movie.setDurationMinutes(120);
        movie.setGender("Ficção");
        movie.setClassification(Classification.C14);
        movie.setSessions(List.of());

        movieResponseDTO = new MovieResponseDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getDurationMinutes(),
                movie.getGender(),
                movie.getClassification().getValue()
        );

        movieRequestDTO = new MovieRequestDTO(
                movie.getTitle(),
                movie.getDurationMinutes(),
                movie.getGender(),
                movie.getClassification()
        );
    }

    @Nested
    class GetMovieTests {

        @Test
        void shouldReturnAllMovies() {
            List<Movie> movies = List.of(movie);
            Pageable pageable = PageRequest.of(0, 10);
            Page<Movie> page = new PageImpl<>(movies, pageable, 1);

            when(repository.findAll(pageable)).thenReturn(page);
            when(mapper.toResponseDTO(movie)).thenReturn(movieResponseDTO);
            CustomPageDTO<MovieResponseDTO> result = service.getAllMovies(pageable);

            assertEquals(1, result.content().size());
            assertEquals(movieResponseDTO, result.content().getFirst());
            verify(repository).findAll(pageable);
        }

        @Test
        void shouldReturnMovieById_WhenIdIsValid() {
            when(repository.findById(id)).thenReturn(Optional.of(movie));
            when(mapper.toResponseDTO(movie)).thenReturn(movieResponseDTO);

            MovieResponseDTO result = service.getMovieById(id);

            assertEquals(movieResponseDTO, result);
            verify(repository).findById(id);
        }

        @Test
        void shouldReturnEntityMovieById_WhenIdIsValid() {
            when(repository.findById(id)).thenReturn(Optional.of(movie));

            Movie result = service.getEntityById(id);

            assertEquals(movie, result);
            verify(repository).findById(id);
        }

        @Test
        void shouldThrowResourceNotFoundException_WhenMovieNotFound() {
            when(repository.findById(id)).thenReturn(Optional.empty());

            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                    () -> service.getEntityById(id));

            assertEquals("Movie not found, id: " + id, exception.getMessage());
            verify(repository).findById(id);
        }
    }

    @Test
    void shouldReturnAndCreateMovie_WhenDataIsValid() {
        when(mapper.toEntity(movieRequestDTO)).thenReturn(movie);
        when(mapper.toResponseDTO(movie)).thenReturn(movieResponseDTO);
        when(repository.save(movie)).thenReturn(movie);

        MovieResponseDTO result = service.createMovie(movieRequestDTO);

        assertEquals(movieResponseDTO, result);
        verify(mapper).toEntity(movieRequestDTO);
        verify(mapper).toResponseDTO(movie);
        verify(repository).save(movie);
    }

    @Test
    void shouldUpdateAndReturnMovie_WhenDataIsValid() {
        when(repository.findById(id)).thenReturn(Optional.of(movie));
        when(repository.save(movie)).thenReturn(movie);
        when(mapper.toResponseDTO(movie)).thenReturn(movieResponseDTO);

        MovieResponseDTO result = service.updateMovie(id, movieRequestDTO);

        assertEquals(movieResponseDTO, result);
        verify(repository).findById(id);
        verify(repository).save(movie);
        verify(mapper).toResponseDTO(movie);
    }

    @Test
    void shouldDeleteMovie_WhenIdIsValid() {
        service.deleteMovie(id);
        verify(repository).deleteById(id);
    }
}