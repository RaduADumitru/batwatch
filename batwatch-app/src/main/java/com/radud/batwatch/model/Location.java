package com.radud.batwatch.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue
    private Long id;

    private String details;

    // Location coordinates as stored in PostGIS: longitude then latitude (EPSG:4326)
    // https://postgis.net/documentation/tips/lon-lat-or-lat-lon/
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point locationPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;
}
