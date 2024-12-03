package com.example.rbac.services;

import com.example.rbac.models.Role;
import com.example.rbac.models.User;
import com.example.rbac.repositories.UserRepository;
import com.example.rbac.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    // Method for registering a new user
    public ResponseEntity<?> registerUser(User user) {
        // Use Optional to check if the user exists
        Optional<Optional<User>> existingUser = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encrypt the password
        user.setRole(new Role("USER"));  // Default role as USER
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // Method for user authentication and generating JWT token
    public ResponseEntity<?> authenticateUser(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        String token = jwtUtil.generateToken(user);  // Generate token for the authenticated user
        return ResponseEntity.ok("Bearer " + token);
    }
}
