package com.radud.batwatch.service;

import com.radud.batwatch.exception.AuthenticatedUserNotFoundException;
import com.radud.batwatch.exception.ReportNotFoundException;
import com.radud.batwatch.exception.UserNotFoundException;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Assignment;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.repository.AssignmentRepository;
import com.radud.batwatch.repository.ReportRepository;
import com.radud.batwatch.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private UserContextService userContextService;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    private final AppUser currentUser = AppUser.builder()
            .id(99L)
            .username("admin")
            .build();

    private final Report report = Report.builder()
            .id(1L)
            .build();

    @Test
    void testCreateAssignment_authenticatedUserNotFound() {
        when(userContextService.getCurrentUser()).thenThrow(new AuthenticatedUserNotFoundException("Authenticated user not found"));

        assertThrows(AuthenticatedUserNotFoundException.class,
                () -> assignmentService.createAssignment(1L, 2L));
    }

    @Test
    void testCreateAssignment_reportNotFound() {
        when(userContextService.getCurrentUser()).thenReturn(currentUser);
        when(reportRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(ReportNotFoundException.class,
                () -> assignmentService.createAssignment(1L, 2L));
    }

    @Test
    void testCreateAssignment_assignedUserNotFound() {
        when(userContextService.getCurrentUser()).thenReturn(currentUser);
        when(reportRepository.findById(1L)).thenReturn(java.util.Optional.of(report));
        when(userRepository.findById(2L)).thenReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> assignmentService.createAssignment(1L, 2L));
    }

    @Test
    void testCreateAssignment_success() {
        AppUser assignedUser = AppUser.builder()
                .id(2L)
                .username("assignee")
                .build();
        Assignment savedAssignment = Assignment.builder()
                .id(3L)
                .report(report)
                .assignedByUser(currentUser)
                .assignedUser(assignedUser)
                .build();
        when(userContextService.getCurrentUser()).thenReturn(currentUser);
        when(reportRepository.findById(1L)).thenReturn(java.util.Optional.of(report));
        when(userRepository.findById(2L)).thenReturn(java.util.Optional.of(assignedUser));
        when(assignmentRepository.save(any())).thenReturn(savedAssignment);

        assignmentService.createAssignment(1L, 2L);

        ArgumentCaptor<Assignment> captor = ArgumentCaptor.forClass(Assignment.class);
        verify(assignmentRepository).save(captor.capture());
        Assignment assignmentToSave = captor.getValue();
        assertEquals(report, assignmentToSave.getReport());
        assertEquals(currentUser, assignmentToSave.getAssignedByUser());
        assertEquals(assignedUser, assignmentToSave.getAssignedUser());

        assertEquals(3L, savedAssignment.getId());
        assertEquals(report, savedAssignment.getReport());
        assertEquals(currentUser, savedAssignment.getAssignedByUser());
        assertEquals(assignedUser, savedAssignment.getAssignedUser());
    }
}
