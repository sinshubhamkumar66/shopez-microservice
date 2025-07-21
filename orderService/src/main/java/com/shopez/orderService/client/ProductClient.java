package com.shopez.orderService.client;

import com.shopez.orderService.configuration.FeignClientConfig;
import com.shopez.orderService.payload.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE", url = "http://localhost:8081", configuration = FeignClientConfig.class)
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductResponse getProductById(@PathVariable("id") Long id);

    @PutMapping("/api/products/{id}/reduce")
    void reduseStock(@PathVariable Long id, @RequestParam Long quantity);
}

