package com.radud.batwatch.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateAdminRequest(
        @NotBlank(message = "Username must not be blank")
        String username,

        @Length(min = 6, message = "Admin password must be at least 6 characters long")
        String password
){
}
