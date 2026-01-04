package com.radud.batwatch.request;

import jakarta.validation.constraints.NotBlank;

public record CreateVolunteerRequest(
        @NotBlank(message = "Username must not be blank")
        String username,

        @NotBlank(message = "Password must not be blank")
        String password) {
}
