package com.vinisnzy.cinema.dtos.seat;

import com.vinisnzy.cinema.dtos.session.SessionResponseDTO;

import java.util.UUID;

public record SeatResponseDTO(
        UUID id,
        String code,
        boolean reserved,
        SessionResponseDTO session
) {
}
