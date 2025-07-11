package com.vinisnzy.cinema.services;

import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.dtos.seat.SeatRequestDTO;
import com.vinisnzy.cinema.dtos.seat.SeatResponseDTO;
import com.vinisnzy.cinema.exceptions.ResourceNotFoundException;
import com.vinisnzy.cinema.mappers.SeatMapper;
import com.vinisnzy.cinema.models.Reserve;
import com.vinisnzy.cinema.models.Seat;
import com.vinisnzy.cinema.models.Session;
import com.vinisnzy.cinema.repositories.SeatRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    private SeatRepository repository;

    @Mock
    private SessionService sessionService;

    @Mock
    private SeatMapper mapper;

    @InjectMocks
    private SeatService seatService;

    private Seat seat;
    private SeatResponseDTO responseDTO;
    private SeatRequestDTO requestDTO;

    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        seat = new Seat(id, "A1", true, null, null);
        responseDTO = new SeatResponseDTO(seat.getId(), seat.getCode(), false, null);
        requestDTO = new SeatRequestDTO("A1", UUID.randomUUID());
    }

    @Nested
    class GetSeatTests {

        @Test
        void shouldReturnAllSeatsBySessionId() {
            List<Seat> seats = List.of(seat);
            UUID sessionId = UUID.randomUUID();
            Pageable pageable = PageRequest.of(0, 10);
            Page<Seat> page = new PageImpl<>(seats, pageable, 1);

            when(repository.findAllBySessionId(sessionId, pageable)).thenReturn(page);
            when(mapper.toResponseDTO(seat, sessionService)).thenReturn(responseDTO);

            CustomPageDTO<SeatResponseDTO> result = seatService.getAllSeatsBySessionId(sessionId, pageable);

            assertEquals(responseDTO, result.content().getFirst());
            assertEquals(1, result.content().size());
            verify(repository).findAllBySessionId(sessionId, pageable);
            verify(mapper).toResponseDTO(seat, sessionService);
        }

        @Test
        void shouldReturnSeatById_WhenIdIsValid() {
            when(repository.findById(id)).thenReturn(Optional.of(seat));
            when(mapper.toResponseDTO(seat, sessionService)).thenReturn(responseDTO);

            SeatResponseDTO result = seatService.getSeatById(id);

            assertEquals(responseDTO, result);
            verify(repository).findById(id);
            verify(mapper).toResponseDTO(seat, sessionService);
        }

        @Test
        void shouldThrowResourceNotFoundException_WhenNotFound() {
            when(repository.findById(id)).thenReturn(Optional.empty());

            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                    () -> seatService.getSeatById(id));

            assertEquals("Seat not found, id: " + id, exception.getMessage());
            verify(repository).findById(id);
        }

        @Test
        void shouldReturnAllSeatsByReserveId() {
            List<Seat> seats = List.of(seat);
            UUID reserveId = UUID.randomUUID();
            Pageable pageable = PageRequest.of(0, 10);
            Page<Seat> page = new PageImpl<>(seats, pageable, 1);

            when(repository.findAllByReserveId(reserveId, pageable)).thenReturn(page);
            when(mapper.toResponseDTO(seat, sessionService)).thenReturn(responseDTO);

            CustomPageDTO<SeatResponseDTO> result = seatService.getAllSeatsByReserveId(reserveId, pageable);

            assertEquals(responseDTO, result.content().getFirst());
            assertEquals(1, result.content().size());
            verify(repository).findAllByReserveId(reserveId, pageable);
            verify(mapper).toResponseDTO(seat, sessionService);
        }

        @Test
        void shouldReturnAllReservedSeatsBySessionId() {
            List<Seat> seats = List.of(seat);
            UUID sessionId = UUID.randomUUID();
            Pageable pageable = PageRequest.of(0, 10);
            Page<Seat> page = new PageImpl<>(seats, pageable, 1);

            when(repository.findAllBySessionIdAndReserved(sessionId, true, pageable)).thenReturn(page);
            when(mapper.toResponseDTO(seat, sessionService)).thenReturn(responseDTO);

            CustomPageDTO<SeatResponseDTO> result = seatService.getAllReservedSeatsBySessionId(sessionId, pageable);

            assertEquals(responseDTO, result.content().getFirst());
            assertEquals(1, result.content().size());
            verify(repository).findAllBySessionIdAndReserved(sessionId, true, pageable);
            verify(mapper).toResponseDTO(seat, sessionService);
        }

        @Test
        void shouldReturnAllAvailableSeatsBySessionId() {
            List<Seat> seats = List.of(seat);
            UUID sessionId = UUID.randomUUID();
            Pageable pageable = PageRequest.of(0, 10);
            Page<Seat> page = new PageImpl<>(seats, pageable, 1);

            when(repository.findAllBySessionIdAndReserved(sessionId, false, pageable)).thenReturn(page);
            when(mapper.toResponseDTO(seat, sessionService)).thenReturn(responseDTO);

            CustomPageDTO<SeatResponseDTO> result = seatService.getAllAvailableSeatsBySessionId(sessionId, pageable);

            assertEquals(responseDTO, result.content().getFirst());
            assertEquals(1, result.content().size());
            verify(repository).findAllBySessionIdAndReserved(sessionId, false, pageable);
            verify(mapper).toResponseDTO(seat, sessionService);
        }

        @Test
        void shouldReturnAllSeatsByCodesAndSessionId_WhenCodesAndSessionIdAreValid() {
            UUID sessionId = UUID.randomUUID();
            List<String> codes = List.of("A1", "A2", "A3");
            when(repository.findByCodeInAndSessionId(codes, sessionId)).thenReturn(List.of(seat));

            List<Seat> result = seatService.getAllSeatsByCodesAndSessionId(codes, sessionId);

            assertEquals(1, result.size());
            assertEquals(seat, result.getFirst());
            verify(repository).findByCodeInAndSessionId(codes, sessionId);
        }
    }

    @Test
    void shouldReturnAndCreateSeat_WhenDataIsValid() {
        when(mapper.toEntity(requestDTO, sessionService)).thenReturn(seat);
        when(repository.save(seat)).thenReturn(seat);
        when(mapper.toResponseDTO(seat, sessionService)).thenReturn(responseDTO);

        SeatResponseDTO result = seatService.createSeat(requestDTO);

        assertEquals(result, responseDTO);
        verify(mapper).toEntity(requestDTO, sessionService);
        verify(mapper).toResponseDTO(seat, sessionService);
        verify(repository).save(seat);
    }

    @Test
    void shouldReturnAndUpdateSeat_WhenDataAndIdIsValid() {
        Session session = new Session();
        UUID sessionId = requestDTO.sessionId();

        when(repository.findById(id)).thenReturn(Optional.of(seat));
        when(repository.save(seat)).thenReturn(seat);
        when(sessionService.getEntityById(sessionId)).thenReturn(session);
        when(mapper.toResponseDTO(seat, sessionService)).thenReturn(responseDTO);

        SeatResponseDTO result = seatService.updateSeat(id, requestDTO);

        assertEquals(responseDTO, result);
        assertEquals(requestDTO.code(), result.code());
        verify(repository).findById(id);
        verify(mapper).toResponseDTO(any(Seat.class), any());
        verify(sessionService).getEntityById(sessionId);
    }

    @Test
    void shouldDeleteSeat_WhenIdIsValid() {
        seatService.deleteSeat(id);
        verify(repository).deleteById(id);
    }

    @Test
    void shouldReleaseSeatsFromReserve_WhenSeatsAreFound() {
        Reserve reserve = new Reserve();
        reserve.setId(UUID.randomUUID());

        seat.setReserve(reserve);
        List<Seat> seats = List.of(seat);

        when(repository.saveAll(seats)).thenReturn(seats);
        when(repository.findAllByReserveId(reserve.getId())).thenReturn(seats);

        seatService.releaseSeatsFromReserve(reserve);

        assertFalse(seat.isReserved());
        assertNull(seat.getReserve());
        verify(repository).findAllByReserveId(reserve.getId());
        verify(repository).saveAll(seats);
    }

    @Test
    void shouldNotSaveAll_WhenSeatsNotFound() {
        Reserve reserve = new Reserve();
        reserve.setId(UUID.randomUUID());
        when(repository.findAllByReserveId(reserve.getId())).thenReturn(List.of());

        seatService.releaseSeatsFromReserve(reserve);

        verify(repository).findAllByReserveId(reserve.getId());
        verify(repository, never()).saveAll(any());
    }
}