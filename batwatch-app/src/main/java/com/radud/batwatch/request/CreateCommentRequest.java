package com.radud.batwatch.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCommentRequest(
        @Schema(description = "Id of the report for which the comment is made", example = "123")
        @NotNull(message = "reportId must not be null")
        Long reportId,

        @Schema(description = "Textual content of the comment", example = "This is a comment.")
        @NotBlank(message = "content must not be blank")
        String content
) {
}
