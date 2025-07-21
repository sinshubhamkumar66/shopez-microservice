package com.shopez.ProductService.globalException;

public class ProductNotFound extends RuntimeException{
    ProductNotFound(){
        super("Product not found..");
    }

    public ProductNotFound(String message){
        super(message);
    }
}
