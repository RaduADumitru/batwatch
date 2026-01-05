package com.radud.batwatch.mapper;

import com.radud.batwatch.model.Status;
import com.radud.batwatch.request.CreateStatusRequest;
import com.radud.batwatch.response.StatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatusMapper {

    Status toModel(CreateStatusRequest req);

    @Mapping(target = "reportId", expression = "java(status.getReport().getId())")
    StatusResponse toResponse(Status status);
}
