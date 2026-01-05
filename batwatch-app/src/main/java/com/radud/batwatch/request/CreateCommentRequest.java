package com.radud.batwatch.request;

public record CreateCommentRequest(
        Long reportId,
        String content
) {
}
