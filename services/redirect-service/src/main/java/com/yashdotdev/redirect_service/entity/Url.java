package com.yashdotdev.redirect_service.entity;


import com.yashdotdev.redirect_service.enums.UrlStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "urls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {

    @Id
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UrlStatus status;

    @Column(name = "expires_at")
    private Instant expiresAt;

}