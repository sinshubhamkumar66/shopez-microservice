package com.shopez.ProductService.globalException;

import com.shopez.ProductService.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<ApiResponse> handleException(ProductNotFound productNotFound){

        ApiResponse response = ApiResponse.builder().message(productNotFound.getMessage())
                .success(true)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
