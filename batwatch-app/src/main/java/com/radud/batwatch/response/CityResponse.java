package com.radud.batwatch.response;

public record CityResponse(
        Long id,
        String name,
        double centerLongitude,
        double centerLatitude,
        double radiusInMeters
) {
}
