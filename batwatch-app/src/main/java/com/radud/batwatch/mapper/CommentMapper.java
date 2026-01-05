package com.radud.batwatch.mapper;

import com.radud.batwatch.model.Comment;
import com.radud.batwatch.request.CreateCommentRequest;
import com.radud.batwatch.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toModel(CreateCommentRequest req);

    @Mapping(target = "reportId", expression = "java(comment.getReport().getId())")
    CommentResponse toResponse(Comment comment);
}
