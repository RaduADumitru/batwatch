package com.radud.batwatch.mapper;

import com.radud.batwatch.model.City;
import com.radud.batwatch.request.CreateCityRequest;
import com.radud.batwatch.response.CityResponse;
import com.radud.batwatch.service.PointFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CityMapper {

    @Autowired
    protected PointFactory pointFactory;

    @Mapping(target = "centerPoint", expression = "java(pointFactory.createPoint(req.centerLongitude(), req.centerLatitude()))")
    public abstract City toModel(CreateCityRequest req);

    @Mapping(target = "centerLongitude", expression = "java(city.getCenterPoint().getX())")
    @Mapping(target = "centerLatitude", expression = "java(city.getCenterPoint().getY())")
    public abstract CityResponse toResponse(City city);
}
