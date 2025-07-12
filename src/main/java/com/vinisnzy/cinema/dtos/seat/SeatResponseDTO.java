package com.vinisnzy.cinema.dtos.seat;

import com.vinisnzy.cinema.dtos.session.SessionResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Seat response data")
public record SeatResponseDTO(

        @Schema(example = "887a1bc5-1c58-42bd-afb9-f431820c3b05")
        UUID id,

        @Schema(example = "A1")
        String code,

        @Schema(example = "false")
        boolean reserved,

        SessionResponseDTO session
) {
}
