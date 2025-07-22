package com.shopez.orderService.payload;

import com.shopez.orderService.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderPaymentResponse {
    private Order order;
    private String razorpayOrderId;

    // constructors, getters, setters
}

