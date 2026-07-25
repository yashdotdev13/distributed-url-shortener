package com.yashdotdev.analytic_service.entity.aggregate;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractDimensionAnalytics {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "short_code",
            nullable = false,
            length = 30
    )
    private String shortCode;

    @Column(nullable = false)
    private Long clicks = 0L;

}
