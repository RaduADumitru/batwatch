package com.radud.batwatch.request;

import com.radud.batwatch.model.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateStatusRequest (
        @Schema(description = "Id of the report for which the status is set", example = "123")
        @NotNull(message = "reportId must not be null")
        Long reportId,

        @Schema(description = "Type of the status set", example = "IN_PROGRESS")
        @NotNull(message = "statusType must not be null")
        StatusType statusType
){
}
