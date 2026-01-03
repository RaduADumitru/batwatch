package com.radud.batwatch.mapper;

import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateVolunteerRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static com.radud.batwatch.model.Role.VOLUNTEER;

@Mapper(componentModel = "spring")
public interface VolunteerMapper {

    AppUser createRequestToModel(CreateVolunteerRequest createVolunteerRequest);

    @AfterMapping
    default void setRole(CreateVolunteerRequest request, @MappingTarget AppUser appUser) {
        appUser.getRoles().add(VOLUNTEER);
    }
}