package com.vinisnzy.cinema.services;

import com.vinisnzy.cinema.dtos.CustomPageDTO;
import com.vinisnzy.cinema.exceptions.ResourceNotFoundException;
import com.vinisnzy.cinema.mappers.SeatMapper;
import com.vinisnzy.cinema.models.Seat;
import com.vinisnzy.cinema.dtos.seat.SeatRequestDTO;
import com.vinisnzy.cinema.models.Reserve;
import com.vinisnzy.cinema.dtos.seat.SeatResponseDTO;
import com.vinisnzy.cinema.models.Session;
import com.vinisnzy.cinema.repositories.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository repository;
    private final SessionService sessionService;
    private final SeatMapper seatMapper;

    public CustomPageDTO<SeatResponseDTO> getAllSeatsBySessionId(UUID sessionId, Pageable pageable) {
        Page<Seat> page = repository.findAllBySessionId(sessionId, pageable);
        List<SeatResponseDTO> content = page.getContent().stream()
                .map(seat -> seatMapper.toResponseDTO(seat, sessionService)).toList();
        return new CustomPageDTO<>(content, page.getNumber(), page.getTotalPages(), page.getTotalElements());

    }

    public SeatResponseDTO getSeatById(UUID id) {
        Seat seat = getEntityById(id);
        return seatMapper.toResponseDTO(seat, sessionService);
    }

    public CustomPageDTO<SeatResponseDTO> getSeatsByReserveId(UUID reserveId, Pageable pageable) {
        Page<Seat> page = repository.findAllByReserveId(reserveId, pageable);
        List<SeatResponseDTO> content = page.getContent().stream()
                .map(seat -> seatMapper.toResponseDTO(seat, sessionService)).toList();
        return new CustomPageDTO<>(content, page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

    public CustomPageDTO<SeatResponseDTO> getReservedSeatsBySessionId(UUID sessionId, Pageable pageable) {
        Page<Seat> page = repository.findAllBySessionIdAndReserved(sessionId, true, pageable);
        List<SeatResponseDTO> content = page.getContent().stream()
                .map(seat -> seatMapper.toResponseDTO(seat, sessionService)).toList();
        return new CustomPageDTO<>(content, page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

    public CustomPageDTO<SeatResponseDTO> getAvailableSeatsBySessionId(UUID sessionId, Pageable pageable) {
        Page<Seat> page = repository.findAllBySessionIdAndReserved(sessionId, false, pageable);
        List<SeatResponseDTO> content = page.getContent().stream()
                .map(seat -> seatMapper.toResponseDTO(seat, sessionService)).toList();
        return new CustomPageDTO<>(content, page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

    public SeatResponseDTO createSeat(SeatRequestDTO data) {
        Seat seat = seatMapper.toEntity(data, sessionService);
        return seatMapper.toResponseDTO(repository.save(seat), sessionService);
    }

    public SeatResponseDTO updateSeat(UUID id, SeatRequestDTO data) {
        Seat seat = getEntityById(id);

        Session session = sessionService.getEntityById(data.sessionId());
        seat.setCode(data.code());
        seat.setSession(session);

        return seatMapper.toResponseDTO(repository.save(seat), sessionService);
    }

    public void deleteSeat(UUID id) {
        repository.deleteById(id);
    }

    public List<Seat> getSeatsByCodesAndSessionId(List<String> codes, UUID sessionId) {
        return repository.findByCodeInAndSessionId(codes, sessionId);
    }

    @Transactional
    public void releaseSeatsFromReserve(Reserve reserve) {
        List<Seat> seats = repository.findAllByReserveId(reserve.getId());
        
        if (!seats.isEmpty()) {
            seats.forEach(seat -> {
                seat.setReserved(false);
                seat.setReserve(null);
            });
            repository.saveAll(seats);
        }
    }

    private Seat getEntityById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found, id: " + id));
    }
}
