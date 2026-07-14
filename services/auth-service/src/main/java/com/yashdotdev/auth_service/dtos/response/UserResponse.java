package com.yashdotdev.auth_service.dtos.response;


import com.yashdotdev.auth_service.enums.AccountStatus;
import com.yashdotdev.auth_service.enums.AuthProvider;
import com.yashdotdev.auth_service.enums.Role;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;
    private AccountStatus status;
    private AuthProvider authProvider;
    private boolean emailVerified;
    private Instant createdAt;
}
