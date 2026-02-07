package com.example.demo.Repository;

import com.example.demo.Entities.CartItem;
import com.example.demo.Entities.Product;
import com.example.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
//    Optional<CartItem> findByUserAndId(User user, Long id);

//    Optional<CartItem> findByUserAndProduct(User user, Product product1);
    Optional<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndId(User user, Long id);

    Optional<CartItem> findByUserAndProduct(User user, Product product);
}
