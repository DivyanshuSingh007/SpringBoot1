package com.example.demo.Services;

import com.example.demo.DTO.ProductRequest;
import com.example.demo.DTO.ProductResponse;
import com.example.demo.Entities.Product;
import com.example.demo.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this :: maptoProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return maptoProductResponse(product);
    }

    public ProductResponse addProduct(ProductRequest productRequest){
        Product product = maptoProductEntity(productRequest);
        return maptoProductResponse(productRepository.save(product));
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        return productRepository.findById(productId)
                .map(p -> {
                    p.setName(request.getName());
                    p.setDescription(request.getDescription());
                    p.setPrice(request.getPrice());
                    p.setCategory(request.getCategory());
                    p.setImageUrl(request.getImageUrl());
                    p.setStockQuantity(request.getStockQuantity());
                    return maptoProductResponse(productRepository.save(p));
                }).orElse(null);
    }

    private Product maptoProductEntity(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
        return product;
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
}
