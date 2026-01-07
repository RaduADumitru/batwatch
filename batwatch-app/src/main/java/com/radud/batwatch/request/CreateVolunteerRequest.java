package com.radud.batwatch.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateVolunteerRequest(
        @Schema(description = "Username of the volunteer", example = "volunteer1")
        @NotBlank(message = "Username must not be blank")
        String username,

        @Schema(description = "Password of the volunteer", example = "password")
        @NotBlank(message = "Password must not be blank")
        String password) {
}
