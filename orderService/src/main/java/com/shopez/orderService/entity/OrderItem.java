package com.shopez.orderService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orderItem")
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private Long quantity;
    private double price;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
