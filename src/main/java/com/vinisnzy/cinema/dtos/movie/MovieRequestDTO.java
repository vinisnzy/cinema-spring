package com.vinisnzy.cinema.dtos.movie;

import com.vinisnzy.cinema.enums.Classification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovieRequestDTO(

        @NotBlank(message = "Title is required")
        String title,

        @NotNull(message = "Duration is required")
        Integer durationMinutes,

        @NotBlank(message = "Gender is required")
        String gender,

        @NotNull(message = "Classification is required")
        Classification classification
) {
}
