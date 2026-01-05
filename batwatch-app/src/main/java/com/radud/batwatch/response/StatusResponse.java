package com.radud.batwatch.response;

import com.radud.batwatch.model.StatusType;

import java.time.Instant;

public record StatusResponse(
        Long id,
        Instant setAt,
        StatusType statusType,
        Long reportId,
        UserResponse setByUser
) {
}
