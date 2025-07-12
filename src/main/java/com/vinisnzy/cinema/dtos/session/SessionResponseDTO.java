package com.vinisnzy.cinema.dtos.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vinisnzy.cinema.dtos.movie.MovieResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Session response data")
public record SessionResponseDTO(

        @Schema(example = "e46d42fb-77bd-4ef6-8aaf-f0546f24d0e7")
        UUID id,

        @Schema(example = "Sala 1")
        String room,

        @Schema(example = "18:00 12/07/2025")
        @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
        String dateTime,

        @Schema(example = "120")
        Integer totalCapacity,

        MovieResponseDTO movie
) {
}
