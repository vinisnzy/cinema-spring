package com.vinisnzy.cinema.mappers;

import com.vinisnzy.cinema.models.movie.Movie;
import com.vinisnzy.cinema.models.movie.MovieResponseDTO;
import com.vinisnzy.cinema.models.session.Session;
import com.vinisnzy.cinema.models.session.SessionRequestDTO;
import com.vinisnzy.cinema.models.session.SessionResponseDTO;
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