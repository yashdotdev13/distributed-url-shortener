package com.yashdotdev.auth_service.dtos.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private UserResponse user;

    private TokenResponse token;
}
