package com.example.demo.DTO;

import com.example.demo.Entities.Product;
import lombok.Data;

@Data
public class CartItemRequest {
    private Long productId;
    private Integer quantity;
}
