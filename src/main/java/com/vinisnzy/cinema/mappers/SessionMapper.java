package com.vinisnzy.cinema.mappers;

import com.vinisnzy.cinema.models.Movie;
import com.vinisnzy.cinema.dtos.movie.MovieResponseDTO;
import com.vinisnzy.cinema.models.Session;
import com.vinisnzy.cinema.dtos.session.SessionRequestDTO;
import com.vinisnzy.cinema.dtos.session.SessionResponseDTO;
import com.vinisnzy.cinema.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class SessionMapper {

    private final MovieMapper movieMapper;

    public SessionResponseDTO toResponseDTO(Session session, MovieService movieService) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        MovieResponseDTO movieDto = movieService.toResponseDTO(session.getMovie());
        return new SessionResponseDTO(
                session.getId(),
                session.getRoom(),
                formatter.format(session.getDateTime()),
                session.getTotalCapacity(),
                movieDto
        );
    }

    public Session toEntity(SessionRequestDTO data, MovieService movieService) {
        Movie movie = movieService.getEntityById(data.movieId());
        Session session = new Session();
        session.setRoom(data.room());
        session.setDateTime(data.dateTime());
        session.setTotalCapacity(data.totalCapacity());
        session.setMovie(movie);
        return session;
    }
}