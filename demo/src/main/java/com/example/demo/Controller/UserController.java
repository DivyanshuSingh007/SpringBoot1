package com.example.demo.Controller;

import com.example.demo.DTO.UserRequest;
import com.example.demo.DTO.UserResponse;
import com.example.demo.Entities.User;
import com.example.demo.Services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
//    public UserController(UserService userService){
//        this.userService = userService;
//    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest updatedUser){
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @PostMapping("/")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest newUser){
        return ResponseEntity.ok(userService.addUser(newUser));
    }
}

