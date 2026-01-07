package com.radud.batwatch.service;

import com.radud.batwatch.model.City;
import com.radud.batwatch.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Mock
    private Point point;

    @Test
    void testCreateCity() {
        City cityToSave = City.builder()
                .name("Gotham")
                .centerPoint(point)
                .radiusInMeters(20000L)
                .build();
        City savedCity = City.builder()
                .id(1L)
                .name("Gotham")
                .centerPoint(point)
                .radiusInMeters(20000L)
                .build();

        when(cityRepository.save(cityToSave)).thenReturn(savedCity);

        City response = cityService.createCity(cityToSave);

        assertEquals(1L, response.getId());
        assertEquals("Gotham", response.getName());
        assertEquals(point, response.getCenterPoint());
        assertEquals(20000L, response.getRadiusInMeters());

        verify(cityRepository).save(cityToSave);
        verifyNoMoreInteractions(cityRepository);
    }
}
