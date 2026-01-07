package com.radud.batwatch.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @Schema(description = "Username of the user", example = "user1")
        @NotBlank(message = "Username must not be blank")
        String username,

        @Schema(description = "Password of the user", example = "password")
        @NotBlank(message = "Password must not be blank")
        String password
){
}
