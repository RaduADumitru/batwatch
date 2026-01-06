package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.AssignmentMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Assignment;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.model.Role;
import com.radud.batwatch.request.CreateAssignmentRequest;
import com.radud.batwatch.response.AssignmentResponse;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.AssignmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Set;

import static com.radud.batwatch.model.Role.ADMIN;
import static com.radud.batwatch.model.Role.VOLUNTEER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssignmentControllerTest {

    @Mock
    private AssignmentService assignmentService;

    @Mock
    private AssignmentMapper assignmentMapper;

    @InjectMocks
    private AssignmentController assignmentController;

    @Test
    void testCreateAssignment() {
        CreateAssignmentRequest request = new CreateAssignmentRequest(3L, 2L);
        AppUser currentUser = AppUser.builder()
                .id(1L)
                .username("currentuser")
                .roles(Set.of(ADMIN))
                .build();
        AppUser assignedUser = AppUser.builder()
                .id(2L)
                .username("assigneduser")
                .roles(Set.of(Role.VOLUNTEER))
                .build();
        Report report = Report.builder()
                .id(3L)
                .description("Bat sighting")
                .build();
        Instant assignedAt = Instant.now();
        Assignment savedAssignment = Assignment.builder()
                .id(4L)
                .assignedAt(assignedAt)
                .assignedUser(assignedUser)
                .assignedByUser(currentUser)
                .report(report)
                .build();
        UserResponse currentUserResponse = new UserResponse(1L, "currentuser", Set.of(ADMIN));
        UserResponse assignedUserResponse = new UserResponse(2L, "assigneduser", Set.of(VOLUNTEER));
        AssignmentResponse assignmentResponse = new AssignmentResponse(4L, assignedAt, assignedUserResponse, currentUserResponse, 3L);
        when(assignmentService.createAssignment(request.reportId(), request.assignedUserId()))
                .thenReturn(savedAssignment);
        when(assignmentMapper.toResponse(savedAssignment))
                .thenReturn(assignmentResponse);

        AssignmentResponse response = assignmentController.createAssignment(request);

        assertEquals(4L, response.id());
        assertEquals(assignedAt, response.assignedAt());
        assertEquals("assigneduser", response.assignedUser().username());
        assertEquals("currentuser", response.assignedByUser().username());
        assertEquals(3L, response.reportId());

        verify(assignmentService).createAssignment(request.reportId(), request.assignedUserId());
        verify(assignmentMapper).toResponse(savedAssignment);
        verifyNoMoreInteractions(assignmentService, assignmentMapper);
    }
}
