package com.vinisnzy.cinema.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Response with pagination")
public record CustomPageDTO<T>(
        List<T> content,
        @Schema(example = "0")
        int page,

        @Schema(example = "5")
        int totalPages,

        @Schema(example = "15")
        long totalElements
) {
}
