package com.radud.batwatch.request;

import com.radud.batwatch.model.StatusType;

public record CreateStatusRequest (
        Long reportId,
        StatusType statusType
){
}
