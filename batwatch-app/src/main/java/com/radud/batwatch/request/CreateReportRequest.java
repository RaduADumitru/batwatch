package com.radud.batwatch.request;

import jakarta.validation.constraints.NotBlank;

public record CreateReportRequest(
        @NotBlank(message = "Title must not be blank")
        String title,
        String description) {
}
