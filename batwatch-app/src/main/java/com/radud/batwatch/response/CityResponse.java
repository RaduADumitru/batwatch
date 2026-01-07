package com.radud.batwatch.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CityResponse(
        @Schema(description = "Id of the city", example = "123")
        Long id,

        @Schema(description = "Name of the city", example = "Bucharest")
        String name,

        @Schema(description = "Longitude of the geographic center of the city", example = "26.096306")
        double centerLongitude,

        @Schema(description = "Latitude of the geographic center of the city", example = "44.439663")
        double centerLatitude,

        @Schema(description = "Radius from the center of the city to be considered as the area of the city", example = "11950")
        double radiusInMeters
) {
}
