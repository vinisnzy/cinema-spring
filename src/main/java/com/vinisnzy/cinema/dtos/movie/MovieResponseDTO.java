package com.vinisnzy.cinema.dtos.movie;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Movie response data")
public record MovieResponseDTO(
        @Schema(example = "99d90f96-f9a9-451d-834c-99b6b9945443")
        UUID id,

        @Schema(example = "Vingadores: Ultimato")
        String title,

        @Schema(example = "181")
        Integer durationMinutes,

        @Schema(example = "Ação")
        String gender,

        @Schema(example = "14")
        String classification
) {
}
