package com.radud.batwatch.request;

import com.radud.batwatch.model.StatusType;
import jakarta.validation.constraints.NotNull;

public record CreateStatusRequest (
        @NotNull(message = "reportId must not be null")
        Long reportId,

        @NotNull(message = "statusType must not be null")
        StatusType statusType
){
}
