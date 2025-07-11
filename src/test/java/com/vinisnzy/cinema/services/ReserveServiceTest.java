package com.vinisnzy.cinema.services;

import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.dtos.reserve.ReserveRequestDTO;
import com.vinisnzy.cinema.dtos.reserve.ReserveResponseDTO;
import com.vinisnzy.cinema.exceptions.ResourceNotFoundException;
import com.vinisnzy.cinema.mappers.ReserveMapper;
import com.vinisnzy.cinema.models.Reserve;
import com.vinisnzy.cinema.repositories.ReserveRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReserveServiceTest {

    @Mock
    private ReserveRepository repository;

    @Mock
    private ReserveMapper mapper;

    @Mock
    private SessionService sessionService;

    @Mock
    private SeatService seatService;

    @InjectMocks
    private ReserveService reserveService;

    private Reserve reserve;
    private ReserveResponseDTO responseDTO;
    private ReserveRequestDTO requestDTO;

    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        reserve = new Reserve();
        reserve.setId(id);
        reserve.setClientName("Vinícius");
        reserve.setCreatedAt(LocalDateTime.now());

        responseDTO = new ReserveResponseDTO(
                reserve.getId(),
                reserve.getClientName(),
                reserve.getCreatedAt(),
                List.of(),
                null
        );

        requestDTO = new ReserveRequestDTO(
                "Vinícius",
                List.of("A1", "A2", "A3"),
                UUID.randomUUID()
        );
    }

    @Nested
    class GetReserveTests {

        @Test
        void shouldReturnAllReserves() {
            List<Reserve> reserves = List.of(reserve);
            Pageable pageable = PageRequest.of(0, 10);
            Page<Reserve> page = new PageImpl<>(reserves, pageable, 1);

            when(repository.findAllWithSeats(pageable)).thenReturn(page);
            when(mapper.toResponseDTO(reserve, sessionService)).thenReturn(responseDTO);

            CustomPageDTO<ReserveResponseDTO> result = reserveService.getAllReserves(pageable);

            assertEquals(responseDTO, result.content().getFirst());
            assertEquals(1, result.content().size());
            verify(repository).findAllWithSeats(pageable);
            verify(mapper).toResponseDTO(reserve, sessionService);
        }

        @Test
        void shouldReturnReserveById_WhenIdIsValid() {
            when(repository.findByIdWithSeats(id)).thenReturn(Optional.of(reserve));
            when(mapper.toResponseDTO(reserve, sessionService)).thenReturn(responseDTO);

            ReserveResponseDTO result = reserveService.getReserveById(id);

            assertEquals(responseDTO, result);
            verify(repository).findByIdWithSeats(id);
        }

        @Test
        void shouldReturnReserveEntity_WhenIdIsValid() {
            when(repository.findByIdWithSeats(id)).thenReturn(Optional.of(reserve));

            Reserve result = reserveService.getEntityById(id);

            assertEquals(reserve, result);
            verify(repository).findByIdWithSeats(id);
        }

        @Test void shouldThrowResourceNotFoundException_WhenNotFound() {
            when(repository.findByIdWithSeats(id)).thenReturn(Optional.empty());

            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                    () -> reserveService.getEntityById(id));

            assertEquals("Reserve not found, id: " + id, exception.getMessage());
            verify(repository).findByIdWithSeats(id);
        }
    }

    @Test
    void shouldCreateAndReturnReserve_WhenValidData() {
        when(mapper.toEntity(requestDTO, sessionService, seatService)).thenReturn(reserve);
        when(repository.save(reserve)).thenReturn(reserve);
        when(mapper.toResponseDTO(reserve, sessionService)).thenReturn(responseDTO);

        ReserveResponseDTO result = reserveService.createReserve(requestDTO);

        assertEquals(responseDTO, result);
        verify(repository).save(reserve);
        verify(mapper).toEntity(requestDTO, sessionService, seatService);
        verify(mapper).toResponseDTO(reserve, sessionService);
    }

    @Test
    void shouldUpdateAndReturnReserve_WhenIdIsValidAndValidData() {
        when(repository.findByIdWithSeats(id)).thenReturn(Optional.of(reserve));
        when(repository.save(reserve)).thenReturn(reserve);
        when(mapper.toResponseDTO(reserve, sessionService)).thenReturn(responseDTO);

        ReserveResponseDTO result = reserveService.updateReserve(id, requestDTO);

        assertEquals(responseDTO, result);
        verify(repository).findByIdWithSeats(id);
        verify(repository).save(reserve);
        verify(mapper).toResponseDTO(reserve, sessionService);
    }

    @Test
    void shouldDeleteReserve_WhenIdIsValid() {
        when(repository.findByIdWithSeats(id)).thenReturn(Optional.of(reserve));

        reserveService.deleteReserve(id);

        verify(repository).delete(reserve);
        verify(seatService).releaseSeatsFromReserve(reserve);
    }
}