package com.vinisnzy.cinema.mappers;

import com.vinisnzy.cinema.models.Seat;
import com.vinisnzy.cinema.dtos.seat.SeatRequestDTO;
import com.vinisnzy.cinema.dtos.seat.SeatResponseDTO;
import com.vinisnzy.cinema.models.Session;
import com.vinisnzy.cinema.services.SessionService;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public SeatResponseDTO toResponseDTO(Seat seat, SessionService sessionService) {
        return new SeatResponseDTO(
                seat.getId(),
                seat.getCode(),
                seat.isReserved(),
                sessionService.toResponseDTO(seat.getSession())
        );
    }

    public Seat toEntity(SeatRequestDTO data, SessionService sessionService) {
        Seat seat = new Seat();
        seat.setCode(data.code());
        seat.setReserved(false);
        Session session = sessionService.getEntityById(data.sessionId());
        seat.setSession(session);
        return seat;
    }
}
