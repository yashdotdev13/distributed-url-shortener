package com.yashdotdev.auth_service.dtos.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private UserResponse user;

    private TokenResponse token;
}
