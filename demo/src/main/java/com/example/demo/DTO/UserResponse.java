package com.example.demo.DTO;

import com.example.demo.Entities.UserRole;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import static com.example.demo.Entities.UserRole.Customer;

@Data
public class UserResponse {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private UserRole role = Customer;
    private AddressDTO address;
}
