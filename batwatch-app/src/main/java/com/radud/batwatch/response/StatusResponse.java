package com.radud.batwatch.response;

import com.radud.batwatch.model.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record StatusResponse(
        @Schema(description = "Id of the status", example = "1234")
        Long id,

        @Schema(description = "Time the status was set at", example = "2026-01-07T15:36:53.980240Z")
        Instant setAt,

        @Schema(description = "Type of the status", example = "IN_PROGRESS")
        StatusType statusType,

        @Schema(description = "Id of the report for which the status is set", example = "123")
        Long reportId,

        @Schema(description = "Information about the user who set the status")
        UserResponse setByUser
) {

}
