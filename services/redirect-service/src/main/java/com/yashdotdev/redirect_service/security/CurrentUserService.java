package com.yashdotdev.redirect_service.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CurrentUserService {

    public AuthenticatedUser getCurrentUser(HttpServletRequest request) {

        String userIdHeader = request.getHeader(SecurityConstants.USER_ID);
        String username = request.getHeader(SecurityConstants.USERNAME);
        String rolesHeader = request.getHeader(SecurityConstants.ROLES);

        if (userIdHeader == null || username == null) {
            throw new RuntimeException("Missing authentication headers.");
        }

        Long userId = Long.parseLong(userIdHeader);

        List<String> roles =
                (rolesHeader == null || rolesHeader.isBlank())
                        ? List.of()
                        : Arrays.stream(rolesHeader.split(","))
                        .map(String::trim)
                        .toList();

        return new AuthenticatedUser(
                userId,
                username,
                roles
        );
    }
}