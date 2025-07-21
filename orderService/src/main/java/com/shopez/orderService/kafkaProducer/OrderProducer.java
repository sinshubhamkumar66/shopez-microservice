package com.shopez.orderService.kafkaProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shopez.orderService.entity.Order;
import com.shopez.orderService.entity.OrderItem;
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

        StringBuilder body = new StringBuilder("Thank you! Your order has been placed successfully.\n\nItems:\n");

        for (OrderItem item : order.getOrderItems()) {
            body.append("- ")
                    .append(item.getName())
                    .append(" (Qty: ").append(item.getQuantity())
                    .append(", Price: ₹").append(item.getPrice())
                    .append(")\n");
        }

        EmailNotification notification = new EmailNotification(
                userEmail,
                "Order Confirmation - ID: " + order.getId(),
                body.toString()
        );
        kafkaTemplate.send("order_notification",notification);

    }
}
