package com.radud.batwatch.mapper;

import com.radud.batwatch.model.Assignment;
import com.radud.batwatch.response.AssignmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface AssignmentMapper {

    @Mapping(target = "reportId", expression = "java(assignment.getReport().getId())")
    AssignmentResponse toResponse(Assignment assignment);
}
