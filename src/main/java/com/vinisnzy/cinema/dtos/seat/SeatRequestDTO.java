package com.vinisnzy.cinema.dtos.seat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Seat creation and update data")
public record SeatRequestDTO(
        @Schema(example = "A1")
        @NotBlank(message = "Code is required")
        String code,

        @Schema(example = "e46d42fb-77bd-4ef6-8aaf-f0546f24d0e7")
        @NotNull(message = "Session ID is required")
        UUID sessionId
) {
}
