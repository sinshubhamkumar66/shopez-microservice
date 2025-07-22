package com.shopez.orderService.client;


import com.shopez.orderService.payload.PaymentRequest;

import com.shopez.orderService.payload.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Payment-service", url = "http://localhost:8083")
public interface PaymentClient {

    @PostMapping("/api/payments/create")
    PaymentResponse createPayment(@RequestBody PaymentRequest paymentRequest);

}
