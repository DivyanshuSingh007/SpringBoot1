package com.example.demo.Services;

import com.example.demo.DTO.AuthResponse;
import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.SignupRequest;
import com.example.demo.Entities.User;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.demo.Entities.UserRole.Customer;

@Service
@RequiredArgsConstructor
public class AuthService {

    private UserRepository userRepository;

    /**
     * Handle user login
     * 
     * @param loginRequest contains username and password
     * @return AuthResponse with user details if successful
     */
    public AuthResponse login(LoginRequest loginRequest) {
        AuthResponse response = new AuthResponse();

        try {
            // Find user by username
            Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

            if (userOptional.isEmpty()) {
                response.setSuccess(false);
                response.setMessage("User not found");
                return response;
            }

            User user = userOptional.get();

            // Validate password
            // Note: In production, you should use BCrypt or similar for password hashing
            // For now, using plain text comparison (NOT RECOMMENDED FOR PRODUCTION)
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                response.setSuccess(false);
                response.setMessage("Invalid password");
                return response;
            }

            // Login successful
            response.setSuccess(true);
            response.setMessage("Login successful");
            response.setUserId(user.getId());
            response.setUsername(user.getUsername());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setRole(user.getRole());

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Login failed: " + e.getMessage());
        }

        return response;
    }

    /**
     * Handle user signup/registration
     * 
     * @param signupRequest contains user registration details
     * @return AuthResponse with new user details if successful
     */
    public AuthResponse signup(SignupRequest signupRequest) {
        AuthResponse response = new AuthResponse();

        try {
            // Check if username already exists
            Optional<User> existingUser = userRepository.findByUsername(signupRequest.getUsername());

            if (existingUser.isPresent()) {
                response.setSuccess(false);
                response.setMessage("Username already exists");
                return response;
            }

            // Validate input
            if (signupRequest.getUsername() == null || signupRequest.getUsername().trim().isEmpty()) {
                response.setSuccess(false);
                response.setMessage("Username is required");
                return response;
            }

            if (signupRequest.getPassword() == null || signupRequest.getPassword().length() < 6) {
                response.setSuccess(false);
                response.setMessage("Password must be at least 6 characters");
                return response;
            }

            // Create new user
            User newUser = new User();
            newUser.setUsername(signupRequest.getUsername());
            // Note: In production, hash the password using BCrypt
            newUser.setPassword(signupRequest.getPassword());
            newUser.setFirstName(signupRequest.getFirstName());
            newUser.setLastName(signupRequest.getLastName());

            // Set role (default to Customer if not provided)
            if (signupRequest.getRole() != null) {
                newUser.setRole(signupRequest.getRole());
            } else {
                newUser.setRole(Customer);
            }

            // Save user to database
            User savedUser = userRepository.save(newUser);

            // Prepare success response
            response.setSuccess(true);
            response.setMessage("User registered successfully");
            response.setUserId(savedUser.getId());
            response.setUsername(savedUser.getUsername());
            response.setFirstName(savedUser.getFirstName());
            response.setLastName(savedUser.getLastName());
            response.setRole(savedUser.getRole());

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Signup failed: " + e.getMessage());
        }

        return response;
    }
}
