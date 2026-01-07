package com.radud.batwatch.service;

import com.radud.batwatch.exception.AuthenticatedUserNotFoundException;
import com.radud.batwatch.exception.ReportNotFoundException;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Report;
import com.radud.batwatch.model.Status;
import com.radud.batwatch.repository.ReportRepository;
import com.radud.batwatch.repository.StatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.radud.batwatch.model.Role.VOLUNTEER;
import static com.radud.batwatch.model.StatusType.NEW;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatusServiceTest {

    @Mock
    private UserContextService userContextService;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private StatusService statusService;

    Report report = Report.builder()
            .id(1L)
            .description("Sample report")
            .build();

    AppUser currentUser = AppUser.builder()
            .id(2L)
            .username("volunteer")
            .roles(Set.of(VOLUNTEER))
            .build();

    @Test
    void testCreateStatus_authenticatedUserNotFound() {
        when(userContextService.getCurrentUser()).thenThrow(new AuthenticatedUserNotFoundException("Authenticated user not found"));

        assertThrows(AuthenticatedUserNotFoundException.class,
                () -> statusService.createStatus(1L, NEW));
    }

    @Test
    void testCreateStatus_reportNotFound() {
        when(userContextService.getCurrentUser()).thenReturn(currentUser);
        when(reportRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(ReportNotFoundException.class,
                () -> statusService.createStatus(1L, NEW));
    }

    @Test
    void testCreateStatus_success() {
        when(userContextService.getCurrentUser()).thenReturn(currentUser);
        when(reportRepository.findById(1L)).thenReturn(java.util.Optional.of(report));

        Status savedStatus = Status.builder()
                .id(3L)
                .setByUser(currentUser)
                .statusType(NEW)
                .report(report)
                .build();

        when(statusRepository.save(org.mockito.ArgumentMatchers.any(Status.class))).thenReturn(savedStatus);
        statusService.createStatus(1L, NEW);

        ArgumentCaptor<Status> statusCaptor = ArgumentCaptor.forClass(Status.class);
        verify(statusRepository).save(statusCaptor.capture());

        Status capturedStatus = statusCaptor.getValue();

        assertSame(report, capturedStatus.getReport());
        assertSame(currentUser, capturedStatus.getSetByUser());
        assertEquals(NEW, capturedStatus.getStatusType());

        assertEquals(3L, savedStatus.getId());
        assertSame(report, savedStatus.getReport());
        assertSame(currentUser, savedStatus.getSetByUser());
        assertEquals(NEW, savedStatus.getStatusType());
    }
}
