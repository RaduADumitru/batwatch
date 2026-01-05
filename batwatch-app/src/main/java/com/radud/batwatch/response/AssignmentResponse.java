package com.radud.batwatch.response;

import java.time.Instant;

public record AssignmentResponse(
        Long id,
        Instant assignedAt,
        UserResponse assignedUser,
        UserResponse assignedByUser,
        Long reportId
) {
}
