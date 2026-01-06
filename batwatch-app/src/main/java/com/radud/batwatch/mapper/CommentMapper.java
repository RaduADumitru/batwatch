package com.radud.batwatch.mapper;

import com.radud.batwatch.model.Comment;
import com.radud.batwatch.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface CommentMapper {

    @Mapping(target = "reportId", expression = "java(comment.getReport().getId())")
    CommentResponse toResponse(Comment comment);
}
