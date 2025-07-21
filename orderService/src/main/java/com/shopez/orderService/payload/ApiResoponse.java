package com.shopez.orderService.payload;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiResoponse {

    private String message;
    private boolean success;
    private HttpStatus status;
}
