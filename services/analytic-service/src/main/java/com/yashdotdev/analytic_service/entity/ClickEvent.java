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

}
