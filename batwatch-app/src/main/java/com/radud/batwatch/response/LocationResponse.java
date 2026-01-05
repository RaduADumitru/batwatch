package com.radud.batwatch.response;

public record LocationResponse(
        String details,
        double longitude,
        double latitude
) {
}
