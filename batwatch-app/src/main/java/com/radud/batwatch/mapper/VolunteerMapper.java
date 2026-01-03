package com.radud.batwatch.mapper;

import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateUserRequest;
import com.radud.batwatch.request.CreateVolunteerRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collections;
import java.util.HashSet;

import static com.radud.batwatch.model.Role.USER;
import static com.radud.batwatch.model.Role.VOLUNTEER;

@Mapper(componentModel = "spring")
public interface VolunteerMapper {

    AppUser createRequestToModel(CreateVolunteerRequest createVolunteerRequest);

    @AfterMapping
    default void setRole(CreateUserRequest request, @MappingTarget AppUser appUser) {
        appUser.setRoles(new HashSet<>(Collections.singletonList(VOLUNTEER)));
    }
}