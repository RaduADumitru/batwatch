package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.CommentMapper;
import com.radud.batwatch.model.Comment;
import com.radud.batwatch.request.CreateCommentRequest;
import com.radud.batwatch.response.CommentResponse;
import com.radud.batwatch.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@AllArgsConstructor
@Tag(name = "Comment", description = "Comment management endpoints")
public class CommentController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    @Operation(summary = "Create comment", description = "Create a new comment for a report. Requires authentication.")
    @PostMapping
    public CommentResponse createComment(@Valid @RequestBody CreateCommentRequest request) {
        Comment comment = commentService.createComment(request.reportId(), request.content());
        return commentMapper.toResponse(comment);
    }
}
