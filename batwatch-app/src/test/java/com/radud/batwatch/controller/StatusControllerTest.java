package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.StatusMapper;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.model.Status;
import com.radud.batwatch.request.CreateStatusRequest;
import com.radud.batwatch.response.StatusResponse;
import com.radud.batwatch.response.UserResponse;
import com.radud.batwatch.service.StatusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Set;

import static com.radud.batwatch.model.Role.VOLUNTEER;
import static com.radud.batwatch.model.StatusType.IN_PROGRESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatusControllerTest {

    @Mock
    private StatusService statusService;

    @Mock
    private StatusMapper statusMapper;

    @InjectMocks
    private StatusController statusController;

    @Test
    void testCreateStatus() {
        CreateStatusRequest createStatusRequest = new CreateStatusRequest(1L, IN_PROGRESS);
        Report report = Report.builder().id(1L).build();
        AppUser reportingUser = AppUser.builder()
                .id(2L)
                .username("volunteer")
                .roles(Set.of(VOLUNTEER))
                .build();
        Instant setAt = Instant.now();
        Status createdStatus = Status.builder()
                .id(3L)
                .setAt(setAt)
                .setByUser(reportingUser)
                .statusType(IN_PROGRESS)
                .report(report)
                .build();
        UserResponse reportingUserResponse = new UserResponse(2L, "volunteer", Set.of(VOLUNTEER));
        StatusResponse statusResponse = new StatusResponse(4L, setAt, IN_PROGRESS, 1L, reportingUserResponse);

        when(statusService.createStatus(1L, IN_PROGRESS)).thenReturn(createdStatus);
        when(statusMapper.toResponse(createdStatus)).thenReturn(statusResponse);

        StatusResponse response = statusController.createStatus(createStatusRequest);

        assertEquals(4L, response.id());
        assertEquals(setAt, response.setAt());
        assertEquals(IN_PROGRESS, response.statusType());
        assertEquals(1L, response.reportId());
        assertEquals(2L, response.setByUser().id());
        assertEquals("volunteer", response.setByUser().username());
        assertEquals(Set.of(VOLUNTEER), response.setByUser().roles());

        verify(statusService).createStatus(1L, IN_PROGRESS);
        verify(statusMapper).toResponse(createdStatus);
        verifyNoMoreInteractions(statusService, statusMapper);
    }
}
