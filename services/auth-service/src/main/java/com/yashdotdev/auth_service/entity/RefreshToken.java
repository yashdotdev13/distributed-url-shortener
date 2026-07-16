package com.yashdotdev.auth_service.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(
        name = "refresh_tokens",
        indexes = {

                @Index(
                        name = "idx_refresh_token",
                        columnList = "token",
                        unique = true
                ),

                @Index(
                        name = "idx_refresh_user",
                        columnList = "user_id"
                ),

                @Index(
                        name = "idx_refresh_expiry",
                        columnList = "expires_at"
                )

        }
)
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true,
            length = 512
    )
    private String token;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @Column(
            name = "expires_at",
            nullable = false
    )
    private Instant expiresAt;

    @Builder.Default
    @Column(nullable = false)
    private boolean revoked = false;

}