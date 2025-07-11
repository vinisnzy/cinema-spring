package com.vinisnzy.cinema.services;

import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.dtos.movie.MovieResponseDTO;
import com.vinisnzy.cinema.dtos.session.SessionRequestDTO;
import com.vinisnzy.cinema.dtos.session.SessionResponseDTO;
import com.vinisnzy.cinema.exceptions.ResourceNotFoundException;
import com.vinisnzy.cinema.mappers.SessionMapper;
import com.vinisnzy.cinema.models.Movie;
import com.vinisnzy.cinema.models.Session;
import com.vinisnzy.cinema.repositories.SessionRepository;
import com.vinisnzy.cinema.utils.GenerateSeats;
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

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @Mock
    private SessionRepository repository;

    @Mock
    private MovieService movieService;

    @Mock
    private GenerateSeats generateSeats;

    @Mock
    private SessionMapper mapper;

    @InjectMocks
    private SessionService sessionService;

    private Session session;
    private SessionResponseDTO responseDTO;
    private SessionRequestDTO requestDTO;
    private Movie movie;

    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(UUID.randomUUID());
        movie.setDurationMinutes(120);
        movie.setSessions(new ArrayList<>());

        LocalDateTime dateTime = LocalDateTime.of(2025, 7, 15, 19, 30);
        session = new Session(id, "Sala 1", dateTime, 50, movie, List.of(), new HashSet<>());

        requestDTO = new SessionRequestDTO(session.getRoom(), session.getDateTime(), session.getTotalCapacity(), session.getMovie().getId());
        responseDTO = new SessionResponseDTO(id, session.getRoom(), session.getDateTime().toString(), session.getTotalCapacity(), null);
    }

    @Nested
    class GetSessionTests {

        @Test
        void shouldReturnAllSessions() {
            List<Session> sessions = List.of(session);
            Pageable pageable = PageRequest.of(0, 10);
            Page<Session> page = new PageImpl<>(sessions, pageable, 1);

            when(repository.findAll(pageable)).thenReturn(page);
            when(mapper.toResponseDTO(session, movieService)).thenReturn(responseDTO);

            CustomPageDTO<SessionResponseDTO> result = sessionService.getAllSessions(pageable);

            assertEquals(1, result.content().size());
            assertEquals(responseDTO, result.content().getFirst());
            verify(repository).findAll(pageable);
            verify(mapper).toResponseDTO(session, movieService);
        }

        @Test
        void shouldReturnSessionById_WhenIdIsValid() {
            when(repository.findById(id)).thenReturn(Optional.of(session));
            when(mapper.toResponseDTO(session, movieService)).thenReturn(responseDTO);

            SessionResponseDTO result = sessionService.getSessionById(id);

            assertEquals(responseDTO, result);
            verify(repository).findById(id);
            verify(mapper).toResponseDTO(session, movieService);
        }

        @Test
        void shouldReturnSessionsByMovieId_WhenIdIsValid() {
            List<Session> sessions = List.of(session);
            Pageable pageable = PageRequest.of(0, 10);
            Page<Session> page = new PageImpl<>(sessions, pageable, 1);

            when(repository.findAllByMovieId(id, pageable)).thenReturn(page);
            when(mapper.toResponseDTO(session, movieService)).thenReturn(responseDTO);

            CustomPageDTO<SessionResponseDTO> result = sessionService.getSessionsByMovieId(id, pageable);

            assertEquals(1, result.content().size());
            assertEquals(responseDTO, result.content().getFirst());
            verify(repository).findAllByMovieId(id, pageable);
            verify(mapper).toResponseDTO(session, movieService);
        }

        @Test
        void shouldReturnSessionsByRoom_WhenRoomIsValid() {
            String room = "Sala 1";
            List<Session> sessions = List.of(session);
            Pageable pageable = PageRequest.of(0, 10);
            Page<Session> page = new PageImpl<>(sessions, pageable, 1);

            when(repository.findAllByRoom(room, pageable)).thenReturn(page);
            when(mapper.toResponseDTO(session, movieService)).thenReturn(responseDTO);

            CustomPageDTO<SessionResponseDTO> result = sessionService.getSessionsByRoom(room, pageable);

            assertEquals(1, result.content().size());
            assertEquals(responseDTO, result.content().getFirst());
            verify(repository).findAllByRoom(room, pageable);
            verify(mapper).toResponseDTO(session, movieService);
        }

        @Test
        void shouldReturnSessionEntityById_WhenIdIsValid() {
            when(repository.findById(id)).thenReturn(Optional.of(session));

            Session result = sessionService.getEntityById(id);

            assertEquals(session, result);
            verify(repository).findById(id);
        }

        @Test
        void shouldThrowResourceNotFoundException_WhenNotFound() {
            when(repository.findById(id)).thenReturn(Optional.empty());

            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                    () -> sessionService.getEntityById(id));

            assertEquals("Session not found, id: " + id, exception.getMessage());
            verify(repository).findById(id);
        }
    }

    @Nested
    class CreateSessionTests {
        @Test
        void shouldReturnAndCreateSession_WhenDataIsValid() {
            Session otherSession = new Session();
            otherSession.setId(UUID.randomUUID());
            otherSession.setRoom(session.getRoom());
            otherSession.setDateTime(LocalDateTime.of(2025, 7, 15, 15, 30));
            otherSession.setMovie(movie);

            when(mapper.toEntity(requestDTO, movieService)).thenReturn(session);
            when(mapper.toResponseDTO(session, movieService)).thenReturn(responseDTO);

            when(repository.save(session)).thenReturn(session);
            when(repository.findAll()).thenReturn(List.of(session, otherSession));

            when(movieService.getEntityById(movie.getId())).thenReturn(movie);
            when(generateSeats.createSeatsForSession(session, session.getTotalCapacity())).thenReturn(new ArrayList<>());

            SessionResponseDTO result = sessionService.createSession(requestDTO);

            assertEquals(responseDTO, result);
            assertTrue(movie.getSessions().contains(session));

            verify(repository).findAll();
            verify(repository).save(session);

            verify(mapper).toEntity(requestDTO, movieService);
            verify(mapper).toResponseDTO(session, movieService);

            verify(movieService).getEntityById(movie.getId());
            verify(generateSeats).createSeatsForSession(session, session.getTotalCapacity());
        }

        @Test
        void shouldThrowIllegalArgumentException_WhenSessionTimeConflicts() {
            Session otherSession = new Session();
            otherSession.setId(UUID.randomUUID());
            otherSession.setRoom(session.getRoom());
            otherSession.setDateTime(LocalDateTime.of(2025, 7, 15, 18, 30));
            otherSession.setMovie(movie);

            when(mapper.toEntity(requestDTO, movieService)).thenReturn(session);
            when(repository.findAll()).thenReturn(List.of(session, otherSession));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> sessionService.createSession(requestDTO));

            assertEquals("There is already a session in that time range", exception.getMessage());
            assertFalse(movie.getSessions().contains(session));

            verify(mapper).toEntity(requestDTO, movieService);
            verify(generateSeats, never()).createSeatsForSession(session, session.getTotalCapacity());

            verify(repository).findAll();
            verify(repository, never()).save(session);
        }
    }

    @Test
    void shouldReturnAndUpdateSession_WhenDataAndIdIsValid() {
        when(repository.findById(id)).thenReturn(Optional.of(session));
        when(repository.save(session)).thenReturn(session);

        when(movieService.getEntityById(movie.getId())).thenReturn(movie);
        when(mapper.toResponseDTO(session, movieService)).thenReturn(responseDTO);

        SessionResponseDTO result = sessionService.updateSession(id, requestDTO);

        assertEquals(responseDTO, result);

        verify(repository).findById(id);
        verify(repository).save(session);

        verify(movieService).getEntityById(movie.getId());
        verify(mapper).toResponseDTO(session, movieService);
    }

    @Test
    void shouldDeleteSessionById_WhenIdIsValid() {
        sessionService.deleteSession(id);
        verify(repository).deleteById(id);
    }
}