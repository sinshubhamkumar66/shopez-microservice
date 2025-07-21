package com.shopez.orderService.globalException;

public class OrderNotFound extends RuntimeException{
    OrderNotFound(){
        super( "Order not found..");
    }
    public OrderNotFound(String message){
        super(message);
    }
}
