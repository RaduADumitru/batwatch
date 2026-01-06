package com.radud.batwatch.mapper;

import com.radud.batwatch.model.Location;
import com.radud.batwatch.request.CreateLocationRequest;
import com.radud.batwatch.response.LocationResponse;
import com.radud.batwatch.service.PointFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LocationMapper {

    @Autowired
    protected PointFactory pointFactory;

    @Mapping(target = "locationPoint", expression = "java(pointFactory.createPoint(req.longitude(), req.latitude()))")
    @Mapping(target = "report", ignore = true) // will be set in ReportMapper @AfterMapping
    public abstract Location toModel(CreateLocationRequest req);

    @Mapping(target = "longitude", expression = "java(location.getLocationPoint().getX())")
    @Mapping(target = "latitude", expression = "java(location.getLocationPoint().getY())")
    public abstract LocationResponse toResponse(Location location);
}
