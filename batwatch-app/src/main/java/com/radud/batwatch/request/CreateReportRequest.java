package com.radud.batwatch.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateReportRequest(
        @NotBlank(message = "Title must not be blank")
        String title,

        @NotBlank(message = "Description must not be blank")
        String description,

        @NotEmpty(message = "At least one location must be provided for the report")
        List<@Valid CreateLocationRequest> locations) {
}
