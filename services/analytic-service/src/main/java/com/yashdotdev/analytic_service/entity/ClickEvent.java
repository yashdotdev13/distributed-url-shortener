package com.yashdotdev.analytic_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "click_events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClickEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shortCode;

    private Long userId;

    @Column(columnDefinition = "TEXT")
    private String originalUrl;

    private Instant clickedAt;

    private String ipAddress;

    @Column(columnDefinition = "TEXT")
    private String userAgent;

    @Column(columnDefinition = "TEXT")
    private String referer;

    @Column(name = "browser", length = 50)
    private String browser;

    @Column(name = "operating_system", length = 50)
    private String operatingSystem;

    @Column(name = "device_type", length = 30)
    private String deviceType;

}
