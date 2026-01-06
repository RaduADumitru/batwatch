package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.CommentMapper;
import com.radud.batwatch.model.Comment;
import com.radud.batwatch.request.CreateCommentRequest;
import com.radud.batwatch.response.CommentResponse;
import com.radud.batwatch.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody CreateCommentRequest request) {
        Comment comment = commentService.createComment(request.reportId(), request.content());
        CommentResponse response = commentMapper.toResponse(comment);
        return ResponseEntity.ok(response);
    }
}
