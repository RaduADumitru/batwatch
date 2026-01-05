package com.radud.batwatch.mapper;

import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.request.CreateUserRequest;
import com.radud.batwatch.response.UserResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static com.radud.batwatch.model.Role.USER;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AppUser createRequestToModel(CreateUserRequest createUserRequest);

    @AfterMapping
    default void setRole(CreateUserRequest request, @MappingTarget AppUser appUser) {
        appUser.getRoles().add(USER);
    }

    UserResponse toResponse(AppUser appUser);
}