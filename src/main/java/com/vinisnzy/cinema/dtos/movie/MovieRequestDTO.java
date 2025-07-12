package com.vinisnzy.cinema.dtos.movie;

import com.vinisnzy.cinema.enums.Classification;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Movie creation and update request")
public record MovieRequestDTO(

        @Schema(example = "Duna: Parte Dois")
        @NotBlank(message = "Title is required")
        String title,

        @Schema(example = "165")
        @NotNull(message = "Duration is required")
        Integer durationMinutes,

        @Schema(example = "Ficção Científica")
        @NotBlank(message = "Gender is required")
        String gender,

        @Schema(example = "14")
        @NotNull(message = "Classification is required")
        Classification classification
) {
}
