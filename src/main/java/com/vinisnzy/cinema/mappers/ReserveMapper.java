package com.vinisnzy.cinema.mappers;

import com.vinisnzy.cinema.exceptions.SeatAlreadyReservedException;
import com.vinisnzy.cinema.models.Reserve;
import com.vinisnzy.cinema.dtos.reserve.ReserveRequestDTO;
import com.vinisnzy.cinema.dtos.reserve.ReserveResponseDTO;
import com.vinisnzy.cinema.models.Seat;
import com.vinisnzy.cinema.services.SeatService;
import com.vinisnzy.cinema.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReserveMapper {

    private final SeatMapper seatMapper;

    public ReserveResponseDTO toResponseDTO(Reserve reserve, SessionService sessionService) {
        return new ReserveResponseDTO(
                reserve.getId(),
                reserve.getClientName(),
                reserve.getCreatedAt(),
                reserve.getSeats().stream().map(Seat::getCode).toList(),
                reserve.getSession() != null ? sessionService.toResponseDTO(reserve.getSession()) : null
        );
    }

    public Reserve toEntity(ReserveRequestDTO data, SessionService sessionService, SeatService seatService) {
        UUID sessionId = data.sessionId();
        List<Seat> seats = seatService.getSeatsByCodesAndSessionId(data.seats(), sessionId);
        List<String> reservedSeats = seats.stream()
                .filter(Seat::isReserved)
                .map(Seat::getCode).toList();

        if (!reservedSeats.isEmpty()) {
            throw new SeatAlreadyReservedException("The following seats is already reserved: " + reservedSeats);
        }
        Reserve reserve = new Reserve();
        reserve.setClientName(data.clientName());
        reserve.setCreatedAt(LocalDateTime.now());
        reserve.setSession(sessionService.getEntityById(sessionId));

        seats.forEach(seat -> {
            seat.setReserved(true);
            seat.setReserve(reserve);
            reserve.getSeats().add(seat);
        });
        return reserve;
    }
}
