package com.example.demo.Services;

import com.example.demo.DTO.*;
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

    public boolean addToCart(String userId, CartItemRequest item) {
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()) return false;
        Optional<Product> productOpt = productRepository.findById(item.getProductId());
        if(productOpt.isEmpty()) return false;

        User user = userOpt.get();
        Product product = productOpt.get();

//        if(product.getStockQuantity() < item.getQuantity()) return false;
        Optional<CartItem> cartItemOpt = cartItemRepository.findByUserAndProduct(user, product);

        if(!cartItemOpt.isPresent()){
            if(item.getQuantity() > product.getStockQuantity()) return false;
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(item.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        else{
            CartItem cartItem = cartItemOpt.get();
            if(cartItem.getQuantity() + item.getQuantity() > product.getStockQuantity()) return false;

            cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean removeFromCart(String userId, CartItemRequest item) {
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()) return false;
        Optional<Product> productOpt = productRepository.findById(item.getProductId());
        if(productOpt.isEmpty()) return false;

        User user = userOpt.get();
        Product product = productOpt.get();
        Optional<CartItem> cartItemOpt = cartItemRepository.findByUserAndProduct(user, product);
        if(cartItemOpt.isPresent()){
            CartItem cartItem = cartItemOpt.get();
            if(cartItem.getQuantity() <= item.getQuantity()){
                cartItemRepository.delete(cartItem);
            }
            else {
                cartItem.setQuantity(cartItem.getQuantity() - item.getQuantity());
                cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                cartItemRepository.save(cartItem);
            }
            return true;
        }
        return false;
    }

    public boolean deleteFromCart(String userId, Long id) {

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()) return false;

        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) return false;

        Optional<CartItem> cartItemOpt =
                cartItemRepository.findByUserAndProduct(userOpt.get(), productOpt.get());

        cartItemOpt.ifPresent(cartItemRepository::delete);
        return true;
    }

    public List<CartItemResponse> getAllCartItems(String userId) {

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()) return new ArrayList<>();

        return cartItemRepository.findByUser(userOpt.get())
                .stream()
                .map(this::mapToCartItemResponse)
                .collect(Collectors.toList());
    }

    public CartItemResponse getCartItem(String userId, Long id) {

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()) return null;

        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) return null;

        return cartItemRepository
                .findByUserAndProduct(userOpt.get(), productOpt.get())
                .map(this::mapToCartItemResponse)
                .orElse(null);
    }
    private UserResponse maptoUserResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity(user.getAddress().getCity());
        addressDTO.setState(user.getAddress().getState());
        addressDTO.setCountry(user.getAddress().getCountry());
        addressDTO.setZip(user.getAddress().getZip());
        addressDTO.setStreet(user.getAddress().getStreet());
        userResponse.setAddress(addressDTO);
        return userResponse;
    }
    private ProductResponse maptoProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setCategory(product.getCategory());// private String imageUrl;
        //private String category;
        //private Boolean active;
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setActive(product.getActive());
        productResponse.setStockQuantity(product.getStockQuantity());
        return productResponse;

    }
    private CartItemResponse mapToCartItemResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setId(cartItem.getId());
//        response.setUserResponse(cartItem.getUser());
//        response.setProductResponse(cartItem.getProduct());
        response.setUser(maptoUserResponse(cartItem.getUser()));
        response.setQuantity(cartItem.getQuantity());
        response.setPrice(cartItem.getPrice());
        response.setProduct(maptoProductResponse(cartItem.getProduct()));
        return response;
    }
}

