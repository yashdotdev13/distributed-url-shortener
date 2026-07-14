package com.yashdotdev.auth_service.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long expiresId;
}
