package com.radud.batwatch.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateAdminRequest(
        @Schema(description = "Username of the admin", example = "admin1")
        @NotBlank(message = "Username must not be blank")
        String username,

        @Schema(description = "Password of the admin", example = "adminpass")
        @Length(min = 6, message = "Admin password must be at least 6 characters long")
        String password
){
}
