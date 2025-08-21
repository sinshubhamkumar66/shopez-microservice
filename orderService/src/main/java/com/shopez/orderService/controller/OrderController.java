package com.shopez.orderService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shopez.orderService.entity.Order;
import com.shopez.orderService.entity.Status;
import com.shopez.orderService.payload.ApiResoponse;
import com.shopez.orderService.payload.OrderPaymentResponse;
import com.shopez.orderService.payload.PlaceOrderRequest;
import com.shopez.orderService.security.JwtUtil;
import com.shopez.orderService.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    public OrderController(OrderService orderService, JwtUtil jwtUtil) {
        this.orderService = orderService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @CircuitBreaker(name = "verifyProduct", fallbackMethod = "verifyProductFallBack")
    public ResponseEntity<OrderPaymentResponse> placeOrder(@RequestHeader("Authorization") String authHeader,
                                                           @RequestBody PlaceOrderRequest request) throws JsonProcessingException {
        String token = authHeader.replace("Bearer ", "");
        String userName = jwtUtil.extractUsername(token); // returns "shubham"

        OrderPaymentResponse order = orderService.placeOrder(request, userName);
        return ResponseEntity.ok(order);
    }

    public ResponseEntity<ApiResoponse> verifyProductFallBack(String authHeader,
                                                                      PlaceOrderRequest request,
                                                                      Throwable ex) {
        ApiResoponse fallbackResponse = ApiResoponse.builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .message("Product Service is currently unavailable. Please try again later.")
                .success(false)
                .build();

        return new ResponseEntity<>(fallbackResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }


    @GetMapping("/user/{userName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable String userName) {
        return ResponseEntity.ok(orderService.getUserOrders(userName));
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")// Only ADMINs can update order status
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam Status status
    ) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
}

