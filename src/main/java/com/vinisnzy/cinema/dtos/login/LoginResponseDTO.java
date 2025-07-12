package com.vinisnzy.cinema.dtos.login;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Login token")
public record LoginResponseDTO(
        @Schema(example = "jwt token")
        String token
) {
}
