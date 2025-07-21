package com.shopez.authService.dto;

import com.shopez.authService.entity.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private Role role;
}

