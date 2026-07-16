package com.yashdotdev.auth_service.service;

import com.yashdotdev.auth_service.dtos.request.LoginRequest;
import com.yashdotdev.auth_service.dtos.request.RegisterRequest;
import com.yashdotdev.auth_service.dtos.response.AuthResponse;
import com.yashdotdev.auth_service.entity.User;
import com.yashdotdev.auth_service.enums.AccountStatus;
import com.yashdotdev.auth_service.enums.AuthProvider;
import com.yashdotdev.auth_service.enums.Role;
import com.yashdotdev.auth_service.exceptions.InvalidCredentialsException;
import com.yashdotdev.auth_service.exceptions.UserAlreadyExistsException;
import com.yashdotdev.auth_service.mapper.UserMapper;
import com.yashdotdev.auth_service.repository.UserRepository;
import com.yashdotdev.auth_service.security.CustomUserDetails;
import com.yashdotdev.auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public AuthResponse register(RegisterRequest request) {

        validateRegistration(request);

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(Role.USER));
        user.setProvider(AuthProvider.LOCAL);
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setEmailVerified(true);

        User savedUser = userRepository.save(user);

        CustomUserDetails userDetails =
                new CustomUserDetails(savedUser);

        return AuthResponse.builder()
                .user(userMapper.toResponse(savedUser))
                .token(jwtService.generateTokens(userDetails))
                .build();

    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )

            );
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            User user = userDetails.getUser();

            return AuthResponse.builder()
                    .user(userMapper.toResponse(user))
                    .token(jwtService.generateTokens(userDetails))
                    .build();

        }catch(BadCredentialsException ex){
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }


    private void validateRegistration(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(
                    "Email already registered."
            );

        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(
                    "Username already exists."
            );

        }

    }
}
