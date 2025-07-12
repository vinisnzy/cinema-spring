package com.vinisnzy.cinema.dtos.reserve;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Schema(description = "Reserve creation and update request")
public record ReserveRequestDTO(

        @Schema(example = "User name")
        @NotBlank(message = "Client name is required")
        String clientName,

        @Schema(example = "F10")
        @NotEmpty(message = "Seats are required")
        List<String> seats,

        @Schema(example = "fbef52f1-711c-42f0-ac45-302d6a6a6364")
        @NotNull(message = "Session is required")
        UUID sessionId
) {
}
