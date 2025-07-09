package com.vinisnzy.cinema.services;

import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.exceptions.ResourceNotFoundException;
import com.vinisnzy.cinema.mappers.SessionMapper;
import com.vinisnzy.cinema.models.Movie;
import com.vinisnzy.cinema.models.Seat;
import com.vinisnzy.cinema.models.Session;
import com.vinisnzy.cinema.dtos.session.SessionRequestDTO;
import com.vinisnzy.cinema.dtos.session.SessionResponseDTO;
import com.vinisnzy.cinema.repositories.SessionRepository;
import com.vinisnzy.cinema.utils.GenerateSeats;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public CustomPageDTO<SessionResponseDTO> getAllSessions(Pageable pageable) {
        Page<Session> page = repository.findAll(pageable);
        List<SessionResponseDTO> content = page.getContent().stream()
                .map(session -> sessionMapper.toResponseDTO(session, movieService)).toList();
        return new CustomPageDTO<>(content, page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

    public SessionResponseDTO getSessionById(UUID id) {
        Session session = getEntityById(id);
        return sessionMapper.toResponseDTO(session, movieService);
    }

    public CustomPageDTO<SessionResponseDTO> getSessionsByMovieId(UUID id, Pageable pageable) {
        Page<Session> page = repository.findAllByMovieId(id, pageable);
        List<SessionResponseDTO> content = page.getContent().stream()
                .map(session -> sessionMapper.toResponseDTO(session, movieService)).toList();
        return new CustomPageDTO<>(content, page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

    public CustomPageDTO<SessionResponseDTO> getSessionsByRoom(String room, Pageable pageable) {
        Page<Session> page = repository.findAllByRoom(room, pageable);
        List<SessionResponseDTO> content = page.getContent().stream()
                .map(session -> sessionMapper.toResponseDTO(session, movieService)).toList();
        return new CustomPageDTO<>(content, page.getNumber(), page.getTotalPages(), page.getTotalElements());
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
                .orElseThrow(() -> new ResourceNotFoundException("Session not found, id: " + id));
    }

    public SessionResponseDTO toResponseDTO(Session session) {
        return sessionMapper.toResponseDTO(session, movieService);
    }
}
