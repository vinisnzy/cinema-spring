package com.vinisnzy.cinema.models.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vinisnzy.cinema.models.movie.MovieResponseDTO;

import java.util.UUID;

public record SessionResponseDTO(
        UUID id,
        String room,

        @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
        String dateTime,
        Integer totalCapacity,
        MovieResponseDTO movie
) {
}
