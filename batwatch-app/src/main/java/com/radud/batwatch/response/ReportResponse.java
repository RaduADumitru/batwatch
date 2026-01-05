package com.radud.batwatch.response;

import com.radud.batwatch.model.Assignment;
import com.radud.batwatch.model.Comment;
import com.radud.batwatch.model.Status;

import java.time.Instant;
import java.util.List;

public record ReportResponse(
        Long id,
        String title,
        String description,
        Instant createdOn,
        List<LocationResponse> locations,
        List<AssignmentResponse> assignments,
        List<StatusResponse> statuses,
        List<Comment> comments,
        UserResponse createdByUser
) {
}
