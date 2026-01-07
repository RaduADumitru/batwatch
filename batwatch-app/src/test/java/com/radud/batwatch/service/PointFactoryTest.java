package com.radud.batwatch.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PointFactoryTest {

    @Mock
    private GeometryFactory geometryFactory;

    @InjectMocks
    private PointFactory pointFactory;

    @Test
    void testCreatePoint() {
        double longitude = 10.0;
        double latitude = 20.0;

        Point mockPoint = mock(Point.class);
        when(geometryFactory.createPoint(any(Coordinate.class))).thenReturn(mockPoint);

        Point result = pointFactory.createPoint(longitude, latitude);

        ArgumentCaptor<Coordinate> coordCaptor = ArgumentCaptor.forClass(Coordinate.class);
        verify(geometryFactory).createPoint(coordCaptor.capture());
        Coordinate captured = coordCaptor.getValue();

        assertEquals(longitude, captured.x);
        assertEquals(latitude, captured.y);
        assertSame(mockPoint, result);
    }
}
