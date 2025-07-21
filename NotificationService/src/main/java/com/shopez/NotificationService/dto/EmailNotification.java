package com.shopez.NotificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotification {
    private String to;
    private String subject;
    private String body;
}
