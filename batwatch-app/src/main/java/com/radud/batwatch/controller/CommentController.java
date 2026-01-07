package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.CommentMapper;
import com.radud.batwatch.model.Comment;
import com.radud.batwatch.request.CreateCommentRequest;
import com.radud.batwatch.response.CityResponse;
import com.radud.batwatch.response.CommentResponse;
import com.radud.batwatch.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created comment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Request lacks valid authentication credentials",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Report not found",
                    content = @Content(mediaType = "text/plain", examples = { @ExampleObject(value = "Invalid report id: 213521") }))
    })
    @PostMapping
    public CommentResponse createComment(@Valid @RequestBody CreateCommentRequest request) {
        Comment comment = commentService.createComment(request.reportId(), request.content());
        return commentMapper.toResponse(comment);
    }
}
