package com.radud.batwatch.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCommentRequest(
        @NotNull(message = "reportId must not be null")
        Long reportId,

        @NotBlank(message = "content must not be blank")
        String content
) {
}
