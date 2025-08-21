package com.shopez.ProductService.globalException;

public class ProductNotFound extends RuntimeException{

    public ProductNotFound(String message){
        super(message);
    }
}
