package com.yashdotdev.auth_service.entity;


import com.yashdotdev.auth_service.enums.AccountStatus;
import com.yashdotdev.auth_service.enums.AuthProvider;
import com.yashdotdev.auth_service.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(
        name = "users",
        indexes = {

                @Index(
                        name = "idx_user_email",
                        columnList = "email"
                ),

                @Index(
                        name = "idx_user_username",
                        columnList = "username"
                ),

                @Index(
                        name = "idx_user_provider",
                        columnList = "provider"
                )

        },

        uniqueConstraints = {

                @UniqueConstraint(
                        name = "uk_user_email",
                        columnNames = "email"
                ),

                @UniqueConstraint(
                        name = "uk_user_username",
                        columnNames = "username"
                )

        }
)
public class User extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 150)
    private String email;

    @Column(nullable = false,length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private AuthProvider provider = AuthProvider.LOCAL;

    @Builder.Default
    @Column(nullable = false)
    private boolean emailVerified = false;

    @ElementCollection(fetch = FetchType.EAGER)

    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

}
