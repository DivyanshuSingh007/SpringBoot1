package com.example.demo.Controller;

import com.example.demo.DTO.AuthResponse;
import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.SignupRequest;
import com.example.demo.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allow all origins - configure as needed for production
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Login endpoint
     * POST /api/auth/login
     * 
     * @param loginRequest JSON body with username and password
     * @return AuthResponse with user details or error message
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Validate input
            if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
                AuthResponse errorResponse = new AuthResponse();
                errorResponse.setSuccess(false);
                errorResponse.setMessage("Username and password are required");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            AuthResponse response = authService.login(loginRequest);

            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

        } catch (Exception e) {
            AuthResponse errorResponse = new AuthResponse();
            errorResponse.setSuccess(false);
            errorResponse.setMessage("Login error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Signup endpoint
     * POST /api/auth/signup
     * 
     * @param signupRequest JSON body with user registration details
     * @return AuthResponse with new user details or error message
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest signupRequest) {
        try {
            // Validate input
            if (signupRequest.getUsername() == null || signupRequest.getPassword() == null) {
                AuthResponse errorResponse = new AuthResponse();
                errorResponse.setSuccess(false);
                errorResponse.setMessage("Username and password are required");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            AuthResponse response = authService.signup(signupRequest);

            if (response.isSuccess()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            AuthResponse errorResponse = new AuthResponse();
            errorResponse.setSuccess(false);
            errorResponse.setMessage("Signup error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Health check endpoint
     * GET /api/auth/health
     * 
     * @return Simple status message
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth service is running");
    }
}
