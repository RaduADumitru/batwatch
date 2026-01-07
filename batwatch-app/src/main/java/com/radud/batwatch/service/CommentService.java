package com.radud.batwatch.service;

import com.radud.batwatch.exception.ReportNotFoundException;
import com.radud.batwatch.model.Comment;
import com.radud.batwatch.repository.CommentRepository;
import com.radud.batwatch.repository.ReportRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final UserContextService userContextService;

    private final CommentRepository commentRepository;

    private final ReportRepository reportRepository;

    @Transactional
    public Comment createComment(@Nonnull Long reportId, @Nonnull String content) {
        // get current user
        var currentUser = userContextService.getCurrentUser();
        // check if report id is valid
        var report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException("Invalid report id: " + reportId));
        // instantiate object and save
        Comment comment = Comment.builder()
                .report(report)
                .createdByUser(currentUser)
                .content(content)
                .build();
        return commentRepository.save(comment);
    }
}
