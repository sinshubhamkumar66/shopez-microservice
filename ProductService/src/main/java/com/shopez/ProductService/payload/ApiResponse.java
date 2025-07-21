package com.shopez.ProductService.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Builder
@Data
public class ApiResponse {
    private String message;
    private boolean success;
    private HttpStatus status;
}
