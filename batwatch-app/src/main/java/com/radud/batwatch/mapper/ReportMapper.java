package com.radud.batwatch.mapper;

import com.radud.batwatch.model.*;
import com.radud.batwatch.request.CreateReportRequest;
import com.radud.batwatch.response.ReportResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
        LocationMapper.class,
        CommentMapper.class,
        StatusMapper.class,
        AssignmentMapper.class
})
public interface ReportMapper {

    Report toModel(CreateReportRequest createReportRequest);

    @AfterMapping
    default void linkChildrenToReport(CreateReportRequest request, @MappingTarget Report report) {
        if (report.getLocations() != null) {
            for (Location loc : report.getLocations()) {
                loc.setReport(report);
            }
        }
    }

    ReportResponse toResponse(Report report);
}
