package com.vinisnzy.cinema.dtos.register;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Register request")
public record RegisterRequestDTO(

        @Schema(example = "username")
        @NotBlank(message = "Username is required")
        String username,

        @Schema(example = "password")
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password
) {
}
