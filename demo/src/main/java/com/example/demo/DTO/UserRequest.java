package com.example.demo.DTO;

import com.example.demo.Entities.UserRole;
import lombok.Data;

import static com.example.demo.Entities.UserRole.Customer;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private AddressDTO address;
}
