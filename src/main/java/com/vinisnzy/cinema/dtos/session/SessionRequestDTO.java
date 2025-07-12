package com.vinisnzy.cinema.dtos.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Session creation and update data")
public record SessionRequestDTO(

        @Schema(example = "Sala 1")
        @NotBlank(message = "Room is required")
        String room,

        @Schema(example = "18:00 12/07/2025")
        @NotNull(message = "Date time is required")
        @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
        @Future(message = "Date time must be in the future")
        LocalDateTime dateTime,

        @Schema(example = "120")
        @Min(value = 20, message = "Total capacity must be greater than 20")
        @Max(value = 200, message = "Total capacity must be less than 200")
        @NotNull(message = "Total capacity is required")
        Integer totalCapacity,

        @Schema(example = "fbef52f1-711c-42f0-ac45-302d6a6a6364")
        @NotNull(message = "Movie ID is required")
        UUID movieId
) {
}
