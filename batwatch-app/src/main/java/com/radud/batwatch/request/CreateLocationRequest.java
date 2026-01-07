package com.radud.batwatch.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request record for creating a new location with longitude and latitude.
 * Based on WGS84 coordinate system / SRID 4326 specification.
 */
public record CreateLocationRequest(
        @Schema(description = "Textual details of the location", example = "Bat in garden near the oak tree")
        @NotBlank(message = "Details must be provided for the location")
        String details,

        @Schema(description = "Longitude of the location", example = "26.124994")
        @NotNull(message = "Longitude must not be null")
        @Min(value = -180, message = "Longitude must be >= -180")
        @Max(value = 180, message = "Longitude must be <= 180")
        double longitude,

        @Schema(description = "Latitude of the location", example = "44.428436")
        @NotNull(message = "Latitude must not be null")
        @Min(value = -90, message = "Latitude must be >= -90")
        @Max(value = 90, message = "Latitude must be <= 90")
        double latitude) {
}
