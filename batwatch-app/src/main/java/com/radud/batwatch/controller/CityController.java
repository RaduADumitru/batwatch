package com.radud.batwatch.controller;

import com.radud.batwatch.mapper.CityMapper;
import com.radud.batwatch.model.City;
import com.radud.batwatch.request.CreateCityRequest;
import com.radud.batwatch.response.CityResponse;
import com.radud.batwatch.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
@AllArgsConstructor
@Tag(name = "City", description = "City management endpoints")
public class CityController {

    private final CityService cityService;

    private final CityMapper cityMapper;

    @Operation(summary = "Create city", description = "Create a new city. Its approximate area is determined via coordinates of its geographic center and a radius. This will then allow searches for reports inside this city. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created city",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CityResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Request lacks valid authentication credentials",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authenticated user does not have required ADMIN role",
                    content = @Content) })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CityResponse createCity(@Valid @RequestBody CreateCityRequest request) {
        City city = cityMapper.toModel(request);
        City createdCity = cityService.createCity(city);
        return cityMapper.toResponse(createdCity);
    }
}
