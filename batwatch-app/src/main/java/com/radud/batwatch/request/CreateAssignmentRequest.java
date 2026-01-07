package com.radud.batwatch.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateAssignmentRequest (
        @Schema(description = "Id of the report for which the assignment is made", example = "123")
        @NotNull(message = "reportId must not be null")
        Long reportId,

        @Schema(description = "Id of the user assigned to the report", example = "456")
        @NotNull(message = "assignedUserId must not be null")
        Long assignedUserId
){
}
