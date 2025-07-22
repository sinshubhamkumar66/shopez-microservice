package com.shopez.PaymentService.service;

import com.shopez.PaymentService.dto.PaymentRequest;
import com.shopez.PaymentService.dto.PaymentResponse;
import com.shopez.PaymentService.entity.Payment;
import com.shopez.PaymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepo;


    public PaymentResponse makePayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setCreatedAt(LocalDateTime.now());

        boolean success = request.getAmount() <= 1000; // dummy condition
        payment.setStatus(success ? "SUCCESS" : "FAILED");
        payment.setTransactionId(UUID.randomUUID().toString());
        paymentRepo.save(payment);
        return new PaymentResponse(
                request.getOrderId(),
                payment.getStatus(),
                payment.getTransactionId()
        );
    }
}

