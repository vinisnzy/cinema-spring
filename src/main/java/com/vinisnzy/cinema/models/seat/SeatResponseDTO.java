package com.vinisnzy.cinema.models.seat;

import com.vinisnzy.cinema.models.session.SessionResponseDTO;

import java.util.UUID;

public record SeatResponseDTO(
        UUID id,
        String code,
        boolean reserved,
        SessionResponseDTO session
) {
}
