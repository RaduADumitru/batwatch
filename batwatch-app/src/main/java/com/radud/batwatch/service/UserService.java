package com.radud.batwatch.service;

import com.radud.batwatch.exception.DuplicateUserException;
import com.radud.batwatch.model.AppUser;
import com.radud.batwatch.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AppUser createUser(AppUser user) {
        String username = user.getUsername();
        Optional<AppUser> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new DuplicateUserException("User named " + username + " already exists");
        }
        user.setPassword(encodePassword(user.getPassword()));
        return userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
