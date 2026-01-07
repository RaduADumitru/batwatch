package com.radud.batwatch.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record CreateCityRequest(
        @Schema(description = "Name of the city", example = "Bucharest")
        @NotBlank(message = "Name cannot be blank")
        String name,

        @Schema(description = "Longitude of the geographic center of the city", example = "26.096306")
        @NotNull(message = "Center longitude must not be null")
        @Min(value = -180, message = "Center longitude must be >= -180")
        @Max(value = 180, message = "Center longitude must be <= 180")
        double centerLongitude,

        @Schema(description = "Latitude of the geographic center of the city", example = "44.439663")
        @NotNull(message = "Center latitude must not be null")
        @Min(value = -90, message = "Center latitude must be >= -90")
        @Max(value = 90, message = "Center latitude must be <= 90")
        double centerLatitude,

        @Schema(description = "Radius from the center of the city to be considered as the area of the city", example = "11950")
        @NotNull(message = "Radius must not be null")
        @Positive(message = "Radius must be positive")
        double radiusInMeters
) {
}
