package com.yashdotdev.url_service.security;


import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;

@Service
public class CurrentUserService {

    public AuthenticatedUser getCurrentUser(ServerWebExchange exchange) {

        String userIdHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(SecurityConstants.USER_ID);

        String username = exchange.getRequest()
                .getHeaders()
                .getFirst(SecurityConstants.USERNAME);

        String rolesHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(SecurityConstants.ROLES);

        Long userId = Long.parseLong(userIdHeader);

        List<String> roles =
                rolesHeader == null || rolesHeader.isBlank()
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
