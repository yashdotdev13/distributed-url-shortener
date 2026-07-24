package com.yashdotdev.analytic_service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlAnalytics {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "short_code",
            nullable = false,
            unique = true,
            length = 12
    )
    private String shortCode;


    @Column(name = "total_clicks")
    private Long totalClicks;

    @Column(name = "unique_clicks")
    private Long uniqueClicks;

    @Column(name = "last_clicked_at")
    private Instant lastClickedAt;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {

        Instant now = Instant.now();

        createdAt = now;
        updatedAt = now;

        if (totalClicks == null) {
            totalClicks = 0L;
        }

        if (uniqueClicks == null) {
            uniqueClicks = 0L;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
