package com.shopez.ProductService.service;

import com.shopez.ProductService.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto product);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    ProductDto updateProduct(Long id, ProductDto product);
    void reduceProductQuantity(Long id, Long quentity);

    void deleteProduct(Long id);
}
