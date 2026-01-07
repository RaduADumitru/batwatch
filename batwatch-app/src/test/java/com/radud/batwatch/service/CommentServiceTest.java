package com.radud.batwatch.service;

import com.radud.batwatch.exception.AuthenticatedUserNotFoundException;
import com.radud.batwatch.exception.ReportNotFoundException;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Comment;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.repository.CommentRepository;
import com.radud.batwatch.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private UserContextService userContextService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private CommentService commentService;

    AppUser currentUser = AppUser.builder()
            .id(1L)
            .username("testuser")
            .build();

    @Test
    void testCreateComment_authenticatedUserNotFound() {
        when(userContextService.getCurrentUser()).thenThrow(new AuthenticatedUserNotFoundException("Authenticated user not found"));

        assertThrows(AuthenticatedUserNotFoundException.class, () -> commentService.createComment(1L, "Sample comment"));
    }

    @Test
    void testCreateComment_reportNotFound() {
        when(userContextService.getCurrentUser()).thenReturn(currentUser);
        when(reportRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ReportNotFoundException.class,
                () -> commentService.createComment(1L, "Sample comment"));
    }

    @Test
    void testCreateComment_success() {
        when(userContextService.getCurrentUser()).thenReturn(currentUser);
        Report report = Report.builder()
                .id(1L)
                .build();
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        Comment savedComment = Comment.builder()
                .id(2L)
                .content("Sample comment")
                .report(report)
                .createdByUser(currentUser)
                .build();
        when(commentRepository.save(org.mockito.ArgumentMatchers.any(com.radud.batwatch.model.Comment.class)))
                .thenReturn(savedComment);

        Comment result = commentService.createComment(1L, "Sample comment");

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(captor.capture());
        Comment toBeSavedComment = captor.getValue();
        assertEquals("Sample comment", toBeSavedComment.getContent());
        assertEquals(1L, toBeSavedComment.getReport().getId());
        assertEquals(1L, toBeSavedComment.getCreatedByUser().getId());

        assertEquals(2L, result.getId());
        assertEquals("Sample comment", result.getContent());
        assertEquals(1L, result.getReport().getId());
        assertEquals(1L, result.getCreatedByUser().getId());
    }

}
