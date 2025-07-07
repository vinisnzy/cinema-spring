package com.vinisnzy.cinema.dtos.movie;

import java.util.UUID;

public record MovieResponseDTO(
        UUID id,
        String title,
        Integer durationMinutes,
        String gender,
        String classification
) {
}
