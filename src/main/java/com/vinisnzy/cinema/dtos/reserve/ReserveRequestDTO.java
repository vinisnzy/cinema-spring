package com.vinisnzy.cinema.dtos.reserve;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ReserveRequestDTO(

        @NotBlank(message = "Client name is required")
        String clientName,

        @NotEmpty(message = "Seats are required")
        List<String> seats,

        @NotNull(message = "Session is required")
        UUID sessionId
) {
}
