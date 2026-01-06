package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.CityMapper;
import com.radud.batwatch.model.City;
import com.radud.batwatch.request.CreateCityRequest;
import com.radud.batwatch.response.CityResponse;
import com.radud.batwatch.service.CityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
@AllArgsConstructor
public class CityController {

    private final CityService cityService;

    private final CityMapper cityMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseBody
    public CityResponse createCity(@Valid @RequestBody CreateCityRequest request) {
        City city = cityMapper.toModel(request);
        City createdCity = cityService.createCity(city);
        return cityMapper.toResponse(createdCity);
    }
}
