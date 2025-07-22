package com.shopez.orderService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shopez.orderService.client.PaymentClient;
import com.shopez.orderService.client.ProductClient;
import com.shopez.orderService.entity.Order;
import com.shopez.orderService.entity.OrderItem;
import com.shopez.orderService.entity.Status;
import com.shopez.orderService.globalException.OrderNotFound;
import com.shopez.orderService.kafkaProducer.OrderProducer;
import com.shopez.orderService.payload.*;
import com.shopez.orderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired private ProductClient productClient;

    @Autowired
    private PaymentClient paymentClient;

    public OrderPaymentResponse placeOrder(PlaceOrderRequest request, String userName) throws JsonProcessingException {

    Order order = new Order();
    order.setUserName(userName);
    order.setOrderDate(LocalDateTime.now());
    order.setStatus(Status.PLACED);

    List<OrderItem> items = new ArrayList<>();
    double totalAmount = 0.0;

    for (OrderItemRequest itemReq : request.getItems()) {
        ProductResponse product = productClient.getProductById(itemReq.getProductId());

        if (product.getQuantity() < itemReq.getQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }

        OrderItem item = new OrderItem();
        item.setProductId(product.getId());
        item.setName(product.getName());
        item.setQuantity(itemReq.getQuantity());
        item.setPrice(product.getPrice());
        item.setOrder(order);

        totalAmount += item.getPrice() * item.getQuantity();
        items.add(item);
        productClient.reduseStock(product.getId(), itemReq.getQuantity());
    }

    order.setOrderItems(items);
    order.setAmount(totalAmount); // assuming your Order has an amount field
    orderRepo.save(order);

    // 🔁 Call payment-service
    PaymentRequest paymentRequest = new PaymentRequest(order.getUserName(),order.getId(), totalAmount);
    PaymentResponse paymentResponse = paymentClient.createPayment(paymentRequest);

    orderProducer.sendOrderNotification(userName, order);
    System.out.println("Notification sent successfully");

    // 🔁 Return both order + razorpayOrderId
    return new OrderPaymentResponse(order, paymentResponse.getRazorpayOrderId());
}

    public List<Order> getUserOrders(String userName) {
        return orderRepo.findByUserName(userName);
    }

    public Order updateOrderStatus(Long orderId, Status status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new OrderNotFound("Order not found with id: " + orderId));

        order.setStatus(status);
        return orderRepo.save(order);
    }
}

