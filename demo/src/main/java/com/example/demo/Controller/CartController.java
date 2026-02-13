package com.example.demo.Controller;

import com.example.demo.DTO.CartItemRequest;
import com.example.demo.DTO.CartItemResponse;
import com.example.demo.DTO.ProductRequest;
import com.example.demo.Services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mycart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @GetMapping("/{product_id}")
    public ResponseEntity<CartItemResponse> getCartItem(@RequestHeader("X-User-ID") String userId, @PathVariable Long product_id){
        return ResponseEntity.ok(cartService.getCartItem(userId, product_id));
    }
    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String id, @RequestBody CartItemRequest request) {
        if (!cartService.addToCart(id, request)) {
            return ResponseEntity.badRequest().body("Unable to add the product");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getAllCartItems(@RequestHeader("X-User-ID") String userId){
        return ResponseEntity.ok(cartService.getAllCartItems(userId));
    }
    @PostMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestHeader("X-User-ID") String userId, @RequestBody CartItemRequest request){
        if(!cartService.removeFromCart(userId, request)) return ResponseEntity.badRequest().body("Unable to remove items from the cart");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item(s) removed successfully");
    }
    @DeleteMapping("/delete/{product_id}")
    public ResponseEntity<String> deleteFromCart(@RequestHeader("X-User-ID") String userId, @PathVariable Long product_id){
        if(!cartService.deleteFromCart(userId, product_id)) return ResponseEntity.badRequest().body("Unable to remove item from the cart");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item removed successfully");
    }

}
