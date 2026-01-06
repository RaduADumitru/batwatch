package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.CityMapper;
import com.radud.batwatch.model.City;
import com.radud.batwatch.request.CreateCityRequest;
import com.radud.batwatch.response.CityResponse;
import com.radud.batwatch.service.CityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityControllerTest {

    @Mock
    private CityService cityService;

    @Mock
    private CityMapper cityMapper;

    @InjectMocks
    private CityController cityController;

    @Mock
    private Point centerPoint;

    @Test
    void testCreateCity() {
        CreateCityRequest request = new CreateCityRequest("Moria", 10.0, 20.0, 30.000);
        City city = City.builder()
                .id(1L)
                .name("Moria")
                .centerPoint(centerPoint)
                .radiusInMeters((long) 30.000)
                .build();
        CityResponse cityResponse = new CityResponse(1L, "Moria", 10.0, 20.0, 30.000);
        City savedCity = City.builder()
                .id(1L)
                .name("Moria")
                .centerPoint(centerPoint)
                .radiusInMeters((long) 30.000)
                .build();

        when(cityMapper.toModel(request)).thenReturn(city);
        when(cityService.createCity(city)).thenReturn(savedCity);
        when(cityMapper.toResponse(savedCity)).thenReturn(cityResponse);

        CityResponse response = cityController.createCity(request);

        assertEquals(1L, response.id());
        assertEquals("Moria", response.name());
        assertEquals(10.0, response.centerLongitude());
        assertEquals(20.0, response.centerLatitude());
        assertEquals(30.000, response.radiusInMeters());

        verify(cityMapper).toModel(request);
        verify(cityService).createCity(city);
        verify(cityMapper).toResponse(savedCity);
        verifyNoMoreInteractions(cityMapper, cityService);
    }
}
