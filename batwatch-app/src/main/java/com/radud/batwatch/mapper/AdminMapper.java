package com.radud.batwatch.mapper;

import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateAdminRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static com.radud.batwatch.model.Role.ADMIN;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AppUser toModel(CreateAdminRequest createAdminRequest);

    @AfterMapping
    default void setRole(CreateAdminRequest request, @MappingTarget AppUser appUser) {
        appUser.getRoles().add(ADMIN);
    }
}