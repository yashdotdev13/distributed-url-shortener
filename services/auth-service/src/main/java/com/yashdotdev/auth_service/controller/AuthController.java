package com.yashdotdev.auth_service.controller;

import com.yashdotdev.auth_service.dtos.request.LoginRequest;
import com.yashdotdev.auth_service.dtos.request.LogoutRequest;
import com.yashdotdev.auth_service.dtos.request.RefreshTokenRequest;
import com.yashdotdev.auth_service.dtos.request.RegisterRequest;
import com.yashdotdev.auth_service.dtos.response.AuthResponse;
import com.yashdotdev.auth_service.dtos.response.TokenResponse;
import com.yashdotdev.auth_service.dtos.response.UserResponse;
import com.yashdotdev.auth_service.security.CustomUserDetails;
import com.yashdotdev.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ){
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(
            @Valid
            @RequestBody
            RefreshTokenRequest request
    ) {

        return ResponseEntity.ok(
                authService.refreshToken(request)
        );

    }



    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request) {

        authService.logout(request);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            Authentication authentication
    ) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(
                authService.getCurrentUser(
                        userDetails.getUsername()
                )
        );

    }


}
