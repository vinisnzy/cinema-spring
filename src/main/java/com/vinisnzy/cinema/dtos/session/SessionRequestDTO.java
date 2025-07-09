package com.vinisnzy.cinema.dtos.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.UUID;

public record SessionRequestDTO(

        @NotBlank(message = "Room is required")
        String room,

        @NotNull(message = "Date time is required")
        @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
        @Future(message = "Date time must be in the future")
        LocalDateTime dateTime,

        @Min(value = 20, message = "Total capacity must be greater than 20")
        @Max(value = 200, message = "Total capacity must be less than 200")
        @NotNull(message = "Total capacity is required")
        Integer totalCapacity,

        @NotNull(message = "Movie ID is required")
        UUID movieId
) {
}
