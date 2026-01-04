package com.radud.batwatch.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

@Entity
public class Location {

    @Id
    @GeneratedValue
    private Long id;

    private String details;

    // PostGIS location
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point locationPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;
}
