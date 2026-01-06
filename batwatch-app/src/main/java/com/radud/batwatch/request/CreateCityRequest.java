package com.radud.batwatch.request;

import jakarta.validation.constraints.*;

public record CreateCityRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @Min(value = -180, message = "Center longitude must be >= -180")
        @Max(value = 180, message = "Center longitude must be <= 180")
        double centerLongitude,

        @Min(value = -90, message = "Center latitude must be >= -90")
        @Max(value = 90, message = "Center latitude must be <= 90")
        double centerLatitude,

        @NotNull(message = "Radius must not be null")
        @Positive(message = "Radius must be positive")
        double radiusInMeters
) {
}
