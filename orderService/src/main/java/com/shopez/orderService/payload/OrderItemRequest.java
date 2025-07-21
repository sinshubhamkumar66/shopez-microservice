package com.shopez.orderService.payload;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long productId;
    private Long quantity;
}