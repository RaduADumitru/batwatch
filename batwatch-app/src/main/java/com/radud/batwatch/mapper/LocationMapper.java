package com.radud.batwatch.mapper;

import com.radud.batwatch.model.Location;
import com.radud.batwatch.request.CreateLocationRequest;
import com.radud.batwatch.response.LocationResponse;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LocationMapper {

    @Autowired
    protected GeometryFactory geometryFactory;

    @Mapping(target = "locationPoint", expression = "java(createPoint(req.longitude(), req.latitude()))")
    @Mapping(target = "report", ignore = true) // will be set in ReportMapper @AfterMapping
    public abstract Location toModel(CreateLocationRequest req);

    protected Point createPoint(Double lon, Double lat) {
        if (lon == null || lat == null) return null;
        return geometryFactory.createPoint(new Coordinate(lon, lat)); // lon, lat order
    }

    @Mapping(target = "longitude", expression = "java(location.getLocationPoint().getX())")
    @Mapping(target = "latitude", expression = "java(location.getLocationPoint().getY())")
    public abstract LocationResponse toResponse(Location location);
}
