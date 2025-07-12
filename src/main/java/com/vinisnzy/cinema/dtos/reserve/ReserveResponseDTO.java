package com.vinisnzy.cinema.dtos.reserve;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vinisnzy.cinema.dtos.session.SessionResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Reserve response data")
public record ReserveResponseDTO(

        @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @Schema(example = "Vinícius de Paula")
        String clientName,

        @Schema(example = "17:13:50 07/07/2025")
        @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
        LocalDateTime createdAt,

        List<String> seats,

        SessionResponseDTO session
) {
}
