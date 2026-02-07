package com.example.demo.Services;

import com.example.demo.DTO.CartItemRequest;
import com.example.demo.DTO.CartItemResponse;
import com.example.demo.Entities.CartItem;
import com.example.demo.Entities.Product;
import com.example.demo.Entities.User;
import com.example.demo.Repository.CartItemRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId ,CartItemRequest item){
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if(user.isEmpty()) return false;
        Optional<Product> product = productRepository.findById(item.getProductId());
        if(product.isEmpty()) return false;
        Product product1 = product.get();
        Optional<CartItem> existing = cartItemRepository.findByUserAndProduct(user.get(), product1);
//        Product product = existing.get().getProduct();
//        if(product == null) return false;
        if(product1.getStockQuantity() < item.getQuantity()) return false;


        if(existing.isPresent()) {
            CartItem cartItem = existing.get();
            cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
            cartItem.setPrice(product1.getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user.get());
            cartItem.setProduct(product1);
            cartItem.setQuantity(item.getQuantity());
            cartItem.setPrice(product1.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean removeFromCart(String userId ,CartItemRequest item){
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if(user.isEmpty()) return false;

        Product product = productRepository.findById(item.getProductId()).get();
        Optional<CartItem> cartItem = cartItemRepository.findByUserAndProduct(user.get(), product);
        if(cartItem.isEmpty()) return false;

        CartItem cartItem1 = cartItem.get();
//        Product product1 = productRepository.findById(item.getId()).get();
        if(cartItem1.getQuantity() <= item.getQuantity()){
            cartItemRepository.delete(cartItem1);
        }
        else{
            cartItem1.setQuantity(cartItem1.getQuantity() - item.getQuantity());
            cartItem1.setUser(user.get());
            cartItem1.setPrice(cartItem1.getPrice().multiply(BigDecimal.valueOf(cartItem1.getQuantity())));
            cartItemRepository.save(cartItem1);
        }
        return false;
    }
    public void deleteFromCart(String userId ,CartItemRequest item){
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if(user.isEmpty()) return;
        Optional<Product> product = productRepository.findById(item.getProductId());
        Optional<CartItem> cartItem = cartItemRepository.findByUserAndId((user.get(), product.get());
        if(cartItem.isEmpty()) return;
        CartItem cartItem1 = cartItem.get();
        cartItemRepository.delete(cartItem1);
    }
    public List<CartItemResponse> getAllCartItems(String userId){
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if(user.isEmpty()) return null;
        return cartItemRepository.findByUser(user.get()).stream()
                .map(this :: maptoCartItemResponse)
                .collect(Collectors.toList());
    }

    public CartItemResponse getCartItem(String userId ,CartItemRequest item){
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if(user.isEmpty()) return null;
//        Product product = productRepository.findById(item.getId()).get();
        return cartItemRepository.findByUserAndId(user.get(), item.getId())
                .map(this :: maptoCartItemResponse).get();
    }

    private CartItemResponse maptoCartItemResponse(CartItem cartItem){
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setProduct(cartItem.getProduct());
        cartItemResponse.setQuantity(cartItem.getQuantity());
        cartItemResponse.setUser(cartItem.getUser());
        cartItemResponse.setId(cartItem.getId());
        cartItemResponse.setPrice(cartItem.getPrice());
        return cartItemResponse;
    }
}
