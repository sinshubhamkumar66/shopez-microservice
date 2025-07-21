package com.shopez.orderService.payload;

import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderRequest {
    private List<OrderItemRequest> items;
}
