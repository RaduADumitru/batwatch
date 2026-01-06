package com.radud.batwatch.response;

public record LocationResponse(
        Long id,
        String details,
        double longitude,
        double latitude
) {
}
