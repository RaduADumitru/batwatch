package com.radud.batwatch.response;

import com.radud.batwatch.model.Comment;
import com.radud.batwatch.request.CreateLocationRequest;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

public record ReportResponse(
        @Schema(description = "Id of the report", example = "123")
        Long id,

        @Schema(description = "Title of the report", example = "Found bat in garden")
        String title,

        @Schema(description = "Description of the report with added details", example = "Saw a bat in my garden that could not fly. Could you please help?")
        String description,

        @Schema(description = "Time when the report was made", example = "2026-01-07T15:36:53.980240Z")
        Instant createdOn,

        @ArraySchema(
                schema = @Schema(implementation = LocationResponse.class),
                arraySchema = @Schema(
                        description = "List of relevant geographic locations for the report"
                )
        )
        List<LocationResponse> locations,

        @ArraySchema(
                schema = @Schema(implementation = AssignmentResponse.class),
                arraySchema = @Schema(
                        description = "List of assignments made for the report"
                )
        )
        List<AssignmentResponse> assignments,

        @ArraySchema(
                schema = @Schema(implementation = StatusResponse.class),
                arraySchema = @Schema(
                        description = "List of statuses set for the report"
                )
        )
        List<StatusResponse> statuses,

        @ArraySchema(
                schema = @Schema(implementation = CommentResponse.class),
                arraySchema = @Schema(
                        description = "List of comments made on the report"
                )
        )
        List<CommentResponse> comments,

        @Schema(description = "Information about the user who created the report")
        UserResponse createdByUser
) {
}
