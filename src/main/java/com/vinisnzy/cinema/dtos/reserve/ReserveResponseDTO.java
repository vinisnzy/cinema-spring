package com.vinisnzy.cinema.dtos.reserve;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vinisnzy.cinema.dtos.session.SessionResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
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
