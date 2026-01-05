package com.radud.batwatch.response;

import com.radud.batwatch.model.Role;

import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        Set<Role> roles
) {
}
