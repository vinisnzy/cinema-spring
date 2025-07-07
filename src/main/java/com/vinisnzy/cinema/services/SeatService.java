package com.vinisnzy.cinema.services;

import com.vinisnzy.cinema.mappers.SeatMapper;
import com.vinisnzy.cinema.models.Seat;
import com.vinisnzy.cinema.dtos.seat.SeatRequestDTO;
import com.vinisnzy.cinema.models.Reserve;
import com.vinisnzy.cinema.dtos.seat.SeatResponseDTO;
import com.vinisnzy.cinema.models.Session;
import com.vinisnzy.cinema.repositories.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository repository;
    private final SessionService sessionService;
    private final SeatMapper seatMapper;

    public List<SeatResponseDTO> getAllSeatsBySessionId(UUID sessionId) {
        return repository.findAllBySessionId(sessionId).stream()
                .map(seat -> seatMapper.toResponseDTO(seat, sessionService)).toList();
    }

    public SeatResponseDTO getSeatById(UUID id) {
        Seat seat = getEntityById(id);
        return seatMapper.toResponseDTO(seat, sessionService);
    }

    public List<SeatResponseDTO> getSeatsByReserveId(UUID reserveId) {
        return repository.findAllByReserveId(reserveId).stream()
                .map(seat -> seatMapper.toResponseDTO(seat, sessionService)).toList();
    }

    public List<SeatResponseDTO> getReservedSeatsBySessionId(UUID sessionId) {
        List<Seat> reservedSeats = repository.findAllBySessionIdAndReserved(sessionId, true);
        return reservedSeats.stream()
                .map(seat -> seatMapper.toResponseDTO(seat, sessionService)).toList();
    }

    public List<SeatResponseDTO> getAvailableSeatsBySessionId(UUID sessionId) {
        List<Seat> freeSeats = repository.findAllBySessionIdAndReserved(sessionId, false);
        return freeSeats.stream()
                .map(seat -> seatMapper.toResponseDTO(seat, sessionService)).toList();
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

    public Seat getEntityById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seat not found"));
    }
    public List<Seat> getSeatsByCodesAndSessionId(List<String> codes, UUID sessionId) {
        return repository.findByCodeInAndSessionId(codes, sessionId);
    }

    public void deleteSeat(UUID id) {
        repository.deleteById(id);
    }

    public void saveSeat(Seat seat) {
        repository.save(seat);
    }

    @Transactional
    public void releaseSeatsFromReserve(Reserve reserve) {
        List<Seat> seats = repository.findAllByReserveId(reserve.getId());

        for (Seat seat : seats) {
            seat.setReserved(false);
            seat.setReserve(null);
            repository.saveAndFlush(seat);
        }
    }
}
