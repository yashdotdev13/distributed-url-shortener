package com.yashdotdev.redirect_service.security;

import java.util.List;

public record AuthenticatedUser (

        Long userId,
        String username,

        List<String> roles
){}