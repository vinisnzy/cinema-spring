package com.vinisnzy.cinema.dtos.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vinisnzy.cinema.dtos.movie.MovieResponseDTO;

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
