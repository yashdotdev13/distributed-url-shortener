package com.yashdotdev.auth_service.dtos.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogoutRequest {


    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
