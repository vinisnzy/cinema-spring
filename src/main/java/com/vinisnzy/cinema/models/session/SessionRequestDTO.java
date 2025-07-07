package com.vinisnzy.cinema.models.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record SessionRequestDTO(

        @NotBlank(message = "Room is required")
        String room,

        @NotBlank(message = "Date time is required")
        @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
        @Future(message = "Date time must be in the future")
        LocalDateTime dateTime,

        @NotBlank(message = "Total capacity is required")
        Integer totalCapacity,

        @NotNull(message = "Movie ID is required")
        UUID movieId
) {
}
