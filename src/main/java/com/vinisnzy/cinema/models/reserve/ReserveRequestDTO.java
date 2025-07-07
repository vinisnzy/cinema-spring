package com.vinisnzy.cinema.models.reserve;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record ReserveRequestDTO(

        @NotBlank(message = "Client name is required")
        String clientName,

        @NotEmpty(message = "Seats are required")
        List<String> seats,

        @NotBlank(message = "Session is required")
        UUID sessionId
) {
}
