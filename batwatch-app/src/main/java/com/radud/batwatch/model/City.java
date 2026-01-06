package com.radud.batwatch.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // Location coordinates as stored in PostGIS: longitude then latitude (EPSG:4326)
    // https://postgis.net/documentation/tips/lon-lat-or-lat-lon/
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point centerPoint;

    private Long radiusInMeters;
}
