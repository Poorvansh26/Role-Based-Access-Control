package com.example.rbac.services;

import com.example.rbac.models.Role;
import com.example.rbac.models.User;
import com.example.rbac.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a user with a default role
    public User createUser(User user) {
        // If the user has no role, assign them the default role (USER)
        if (user.getRole() == null) {
            user.setRole(new Role("USER"));  // Default role as USER
        }
        return userRepository.save(user);
    }

    // Get a user by their ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Get a user by their username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    // Delete a user by their ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // You can add additional methods for role management here (e.g., assign roles)
    public void assignRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Role role = new Role(roleName);  // Create a new role (make sure roles are created in the database)
            user.setRole(role);
            userRepository.save(user);
        }
    }
}
