package com.radud.batwatch.configuration;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeometryConfig {

    @Bean
    public GeometryFactory geometryFactory() {
        // SRID 4326 (EPSG:4326) — coordinates in lon,lat order
        return new GeometryFactory(new PrecisionModel(), 4326);
    }
}
