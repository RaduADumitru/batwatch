package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.CityMapper;
import com.radud.batwatch.model.City;
import com.radud.batwatch.request.CreateCityRequest;
import com.radud.batwatch.response.CityResponse;
import com.radud.batwatch.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
@AllArgsConstructor
@Tag(name = "City", description = "City management endpoints")
public class CityController {

    private final CityService cityService;

    private final CityMapper cityMapper;

    @Operation(summary = "Create city", description = "Create a new city. Its approximate area is determined via coordinates of its geographic center and a radius. This will then allow searches for reports inside this city. Requires ADMIN role.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CityResponse createCity(@Valid @RequestBody CreateCityRequest request) {
        City city = cityMapper.toModel(request);
        City createdCity = cityService.createCity(city);
        return cityMapper.toResponse(createdCity);
    }
}
