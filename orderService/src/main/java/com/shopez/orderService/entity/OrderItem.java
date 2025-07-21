package com.shopez.orderService.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String name;
    private Long quantity;
    private double price;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
