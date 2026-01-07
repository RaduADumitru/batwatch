package com.radud.batwatch.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record AssignmentResponse(
        @Schema(description = "Id of the assignment", example = "1234")
        Long id,

        @Schema(description = "Time when the assignment was made", example = "2026-01-07T15:46:53.980240Z")
        Instant assignedAt,

        @Schema(description = "Information about the user who is assigned to the report")
        UserResponse assignedUser,

        @Schema(description = "Information about the user who made the assignment")
        UserResponse assignedByUser,

        @Schema(description = "Id of the report for which the assignment was made", example = "123")
        Long reportId
) {
}
