package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.CityMapper;
import com.radud.batwatch.model.City;
import com.radud.batwatch.request.CreateCityRequest;
import com.radud.batwatch.response.CityResponse;
import com.radud.batwatch.service.CityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
@AllArgsConstructor
public class CityController {

    private final CityService cityService;

    private final CityMapper cityMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCity(@Valid @RequestBody CreateCityRequest request) {
        City city = cityMapper.toModel(request);
        City createdCity = cityService.createCity(city);
        CityResponse response = cityMapper.toResponse(createdCity);
        return ResponseEntity.ok(response);
    }
}
