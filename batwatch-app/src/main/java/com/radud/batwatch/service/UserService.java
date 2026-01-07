package com.radud.batwatch.service;

import com.radud.batwatch.exception.DuplicateUserException;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.model.Role;
import com.radud.batwatch.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AppUser createUser(String username, String password, Role role) {
        Optional<AppUser> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new DuplicateUserException("User named " + username + " already exists");
        }
        String encodedPassword = encodePassword(password);
        AppUser user = AppUser.builder()
                .username(username)
                .password(encodedPassword)
                .roles(Set.of(role))
                .build();
        return userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
