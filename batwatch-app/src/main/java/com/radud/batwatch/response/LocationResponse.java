package com.radud.batwatch.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LocationResponse(
        @Schema(description = "Id of the location", example = "12345")
        Long id,

        @Schema(description = "Textual details of the location", example = "Bat in garden near the oak tree")
        String details,

        @Schema(description = "Longitude of the location", example = "26.124994")
        double longitude,

        @Schema(description = "Latitude of the location", example = "44.428436")
        double latitude
) {
}
