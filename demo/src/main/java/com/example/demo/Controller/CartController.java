package com.example.demo.Controller;

import com.example.demo.DTO.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mycart")
public class CartController {

    @PostMapping("/{userId}")
    public ResponseEntity<Boolean> addToCart(@RequestHeader("X-User-ID") Long id, @RequestBody ProductRequest product){

    }
}
