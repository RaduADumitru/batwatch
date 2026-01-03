package com.radud.batwatch.mapper;

import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateAdminRequest;
import com.radud.batwatch.request.CreateUserRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collections;
import java.util.HashSet;

import static com.radud.batwatch.model.Role.ADMIN;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AppUser createRequestToModel(CreateAdminRequest createAdminRequest);

    @AfterMapping
    default void setRole(CreateUserRequest request, @MappingTarget AppUser appUser) {
        appUser.setRoles(new HashSet<>(Collections.singletonList(ADMIN)));
    }
}