package com.shopez.orderService.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String userName;
    private Long orderId;
    private double amount;

    // constructors, getters, setters
}

