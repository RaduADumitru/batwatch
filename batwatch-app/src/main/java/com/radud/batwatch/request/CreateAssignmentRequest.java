package com.radud.batwatch.request;

public record CreateAssignmentRequest (
        Long reportId,
        Long assignedUserId
){
}
