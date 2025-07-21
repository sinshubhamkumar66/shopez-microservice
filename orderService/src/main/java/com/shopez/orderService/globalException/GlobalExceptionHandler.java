package com.shopez.orderService.globalException;

import com.shopez.orderService.payload.ApiResoponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(OrderNotFound.class)
    public ResponseEntity<ApiResoponse> handleException(OrderNotFound orderNotFound){

        ApiResoponse resoponse = ApiResoponse.builder()
                .message(orderNotFound.getMessage())
                .success(true)
                .status(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(resoponse, HttpStatus.NOT_FOUND);
    }
}
