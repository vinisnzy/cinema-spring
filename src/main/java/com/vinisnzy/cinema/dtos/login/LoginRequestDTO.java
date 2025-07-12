package com.vinisnzy.cinema.dtos.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "User login")
public record LoginRequestDTO(

        @Schema(example = "username")
        @NotBlank(message = "Username is required")
        String username,

        @Schema(example = "password")
        @NotBlank(message = "Password is required")
        String password
) {
}
