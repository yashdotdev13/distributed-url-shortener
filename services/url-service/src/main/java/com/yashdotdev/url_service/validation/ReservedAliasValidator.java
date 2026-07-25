package com.yashdotdev.url_service.validation;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ReservedAliasValidator {

    private static final Set<String> RESERVED = Set.of(

            "api",
            "auth",
            "login",
            "logout",
            "register",
            "admin",
            "actuator",
            "swagger",
            "swagger-ui",
            "v3",
            "health"

    );

    public boolean isReserved(String alias) {

        return RESERVED.contains(
                alias.toLowerCase()
        );
    }
}