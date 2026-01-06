package com.radud.batwatch.request;

import jakarta.validation.constraints.NotNull;

public record CreateAssignmentRequest (
        @NotNull(message = "reportId must not be null")
        Long reportId,

        @NotNull(message = "assignedUserId must not be null")
        Long assignedUserId
){
}
