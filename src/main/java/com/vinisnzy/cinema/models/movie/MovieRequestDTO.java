package com.vinisnzy.cinema.models.movie;

import com.vinisnzy.cinema.enums.Classification;
import jakarta.validation.constraints.NotBlank;

public record MovieRequestDTO(

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Duration is required")
        Integer durationMinutes,

        @NotBlank(message = "Gender is required")
        String gender,

        @NotBlank(message = "Classification is required")
        Classification classification
) {
}
