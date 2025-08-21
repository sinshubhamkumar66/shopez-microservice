package com.shopez.ProductService.service;

import com.shopez.ProductService.dto.ProductDto;
import com.shopez.ProductService.entity.Products;
import com.shopez.ProductService.globalException.ProductNotFound;
import com.shopez.ProductService.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;


    @Override
    public ProductDto createProduct(ProductDto product) {

        Products products = Products.builder()
                .name(product.getName())
                .category(product.getCategory())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
        Products saveProduct = productRepository.save(products);

        return ProductDto.builder()
                .name(saveProduct.getName())
                .category(saveProduct.getCategory())
                .description(saveProduct.getDescription())
                .quantity(saveProduct.getQuantity())
                .price(saveProduct.getPrice())
                .build();

    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductDto> allProduct = productRepository.findAll().stream()
                .map(p-> ProductDto.builder()
                        .price(p.getPrice())
                        .quantity(p.getQuantity())
                        .description(p.getDescription())
                        .category(p.getCategory())
                        .name(p.getName())
                        .build())
                .collect(Collectors.toList());
        return allProduct;
    }

    @Override
    public ProductDto getProductById(Long id) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("Product not found with id: " + id));

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .quantity(product.getQuantity())
                .build();
    }
    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("Product not found" + id));

        // Update product fields
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setQuantity(productDto.getQuantity());

        Products updated = productRepository.save(product);

        return ProductDto.builder()
                .name(updated.getName())
                .description(updated.getDescription())
                .price(updated.getPrice())
                .category(updated.getCategory())
                .quantity(updated.getQuantity())
                .build();
    }

    @Override
    public void reduceProductQuantity(Long id, Long quantity) {
        Optional<Products> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Products product = optionalProduct.get();

            if (product.getQuantity() >= quantity) {
                product.setQuantity(product.getQuantity() - quantity);
                productRepository.save(product);
            } else {
                throw new ProductNotFound("Insufficient quantity for product ID: " + id);
            }

        } else {
            throw new ProductNotFound("Product not found with ID: " + id);
        }
    }


    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFound("Product not found" +id);
        }
        productRepository.deleteById(id);
    }

}
