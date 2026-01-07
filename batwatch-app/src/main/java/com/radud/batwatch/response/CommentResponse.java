package com.radud.batwatch.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record CommentResponse(
        @Schema(description = "Id of the city", example = "1234")
        Long id,

        @Schema(description = "Textual content of the comment", example = "This is a comment.")
        String content,

        @Schema(description = "Time when the comment was created", example = "2026-01-07T15:39:53.980240Z")
        Instant createdOn,

        @Schema(description = "Id of the report for which the comment is made", example = "123")
        Long reportId,

        @Schema(description = "Information about the user who created the comment")
        UserResponse createdByUser
) {
}
