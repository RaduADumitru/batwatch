package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.CommentMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Comment;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.request.CreateCommentRequest;
import com.radud.batwatch.response.CommentResponse;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Set;

import static com.radud.batwatch.model.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentController commentController;

    @Test
    void testCreateComment() {
        CreateCommentRequest request = new CreateCommentRequest(1L, "Sample comment");
        Report commentedReport = Report.builder()
                .id(1L)
                .build();
        AppUser commentingUser = AppUser.builder()
                .id(2L)
                .username("commenter")
                .roles(Set.of(USER))
                .build();
        Instant commentCreatedOn = Instant.now();
        Comment savedComment = Comment.builder()
                .id(3L)
                .createdOn(commentCreatedOn)
                .report(commentedReport)
                .content("Sample comment")
                .createdByUser(commentingUser)
                .build();
        UserResponse commentingUserResponse = new UserResponse(2L, "commenter", Set.of(USER));
        CommentResponse commentResponse = new CommentResponse(3L, "Sample comment", commentCreatedOn, 1L, commentingUserResponse);

        when(commentService.createComment(1L, "Sample comment")).thenReturn(savedComment);
        when(commentMapper.toResponse(savedComment)).thenReturn(commentResponse);

        CommentResponse response = commentController.createComment(request);

        assertEquals(3L, response.id());
        assertEquals("Sample comment", response.content());
        assertEquals(commentCreatedOn, response.createdOn());
        assertEquals(1L, response.reportId());
        assertEquals(commentingUserResponse, response.createdByUser());

        verify(commentService).createComment(1L, "Sample comment");
        verify(commentMapper).toResponse(savedComment);
        verifyNoMoreInteractions(commentService, commentMapper);
    }
}
