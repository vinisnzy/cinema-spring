package com.vinisnzy.cinema.services;

import com.vinisnzy.cinema.mappers.SessionMapper;
import com.vinisnzy.cinema.models.Movie;
import com.vinisnzy.cinema.models.Seat;
import com.vinisnzy.cinema.models.Session;
import com.vinisnzy.cinema.dtos.session.SessionRequestDTO;
import com.vinisnzy.cinema.dtos.session.SessionResponseDTO;
import com.vinisnzy.cinema.repositories.SessionRepository;
import com.vinisnzy.cinema.utils.GenerateSeats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository repository;

    private final MovieService movieService;
    private final GenerateSeats generateSeats;

    private final SessionMapper sessionMapper;

    public List<SessionResponseDTO> getAllSessions() {
        return repository.findAll().stream()
                .map(session -> sessionMapper.toResponseDTO(session, movieService)).toList();
    }

    public SessionResponseDTO getSessionById(UUID id) {
        Session session = getEntityById(id);
        return sessionMapper.toResponseDTO(session, movieService);
    }

    public List<SessionResponseDTO> getSessionsByMovieId(UUID id) {
        return repository.findAllByMovieId(id).stream()
                .map(session -> sessionMapper.toResponseDTO(session, movieService)).toList();
    }

    public List<SessionResponseDTO> getSessionsByRoom(String room) {
        return repository.findAllByRoom(room).stream()
                .map(session -> sessionMapper.toResponseDTO(session, movieService)).toList();
    }

    public SessionResponseDTO createSession(SessionRequestDTO data) {
        Session session = sessionMapper.toEntity(data, movieService);
        Movie movie = movieService.getEntityById(data.movieId());
        session = repository.save(session);
        movie.getSessions().add(session);
        List<Seat> seats = generateSeats.createSeatsForSession(session, data.totalCapacity());
        session.setSeats(seats);
        return sessionMapper.toResponseDTO(session, movieService);
    }

    public SessionResponseDTO updateSession(UUID id, SessionRequestDTO data) {
        Session session = getEntityById(id);
        Movie movie = movieService.getEntityById(data.movieId());

        session.setRoom(data.room());
        session.setDateTime(data.dateTime());
        session.setTotalCapacity(data.totalCapacity());
        session.setMovie(movie);

        return sessionMapper.toResponseDTO(repository.save(session), movieService);
    }

    public void deleteSession(UUID id) {
        repository.deleteById(id);
    }

    public Session getEntityById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));
    }

    public SessionResponseDTO toResponseDTO(Session session) {
        return sessionMapper.toResponseDTO(session, movieService);
    }
}
