package com.radud.batwatch.response;

import com.radud.batwatch.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public record UserResponse(
        @Schema(description = "Id of the user", example = "123")
        Long id,

        @Schema(description = "Username of the user", example = "user1")
        String username,

        @Schema(description = "Roles of the user", example = "[\"USER\"]")
        Set<Role> roles
) {
}
