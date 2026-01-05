package com.radud.batwatch.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Request record for creating a new location with longitude and latitude.
 * Based on WGS84 coordinate system / SRID 4326 specification.
 *
 * @param longitude the longitude of the location, must be between -180 and 180
 * @param latitude  the latitude of the location, must be between -90 and 90
 */
public record CreateLocationRequest(
        @NotBlank(message = "Details must be provided for the location")
        String details,

        @Min(value = -180, message = "Longitude must be >= -180")
        @Max(value = 180, message = "Longitude must be <= 180")
        double longitude,

        @Min(value = -90, message = "Latitude must be >= -90")
        @Max(value = 90, message = "Latitude must be <= 90")
        double latitude) {
}
