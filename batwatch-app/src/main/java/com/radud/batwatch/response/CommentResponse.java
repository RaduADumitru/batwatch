package com.radud.batwatch.response;

import java.time.Instant;

public record CommentResponse(
        Long id,
        String content,
        Instant createdOn,
        Long reportId,
        UserResponse userResponse
) {
}
