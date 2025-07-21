package com.shopez.orderService.kafkaProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopez.orderService.entity.Order;
import com.shopez.orderService.payload.EmailNotification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    private final KafkaTemplate<String, EmailNotification> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, EmailNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendOrderNotification(String userEmail, Order order) throws JsonProcessingException {
        EmailNotification notification = new EmailNotification(
                userEmail,
                "Order Confirmation - ID: " + order.getId(),
                "Thank you! Your order has been placed successfully for " + order.getOrderItems().size() + " items."
        );

        kafkaTemplate.send("order_notification",notification);

    }
}
