package com.example.demo.DTO;

import com.example.demo.Entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private UserRole role;
    private String message;
    private boolean success;
}
