package com.vinisnzy.cinema.models.seat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SeatRequestDTO(
        @NotBlank(message = "Code is required")
        String code,

        @NotNull(message = "Session ID is required")
        UUID sessionId
) {
}
