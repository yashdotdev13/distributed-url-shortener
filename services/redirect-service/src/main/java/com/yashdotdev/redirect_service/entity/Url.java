package com.yashdotdev.redirect_service.entity;


import com.yashdotdev.redirect_service.enums.UrlStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "urls",
        indexes = {

                @Index(
                        name = "idx_short_code",
                        columnList = "short_code",
                        unique = true
                ),

                @Index(
                        name = "idx_user_id",
                        columnList = "user_id"
                )

        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "original_url",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String originalUrl;

    @Column(
            name = "short_code",
            nullable = false,
            unique = true,
            length = 12
    )
    private String shortCode;

    @Column(
            name = "user_id",
            nullable = false
    )
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UrlStatus status;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private Instant createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private Instant updatedAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "last_accessed_at")
    private Instant lastAccessedAt;

    @Column(
            name = "click_count",
            nullable = false
    )
    private Long clickCount;

}