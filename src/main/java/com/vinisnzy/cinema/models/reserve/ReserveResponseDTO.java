package com.vinisnzy.cinema.models.reserve;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vinisnzy.cinema.models.seat.SeatResponseDTO;
import com.vinisnzy.cinema.models.session.SessionResponseDTO;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record ReserveResponseDTO(

        UUID id,
        String clientName,

        @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
        LocalDateTime createdAt,

        List<String> seats,
        SessionResponseDTO session
) {
}
