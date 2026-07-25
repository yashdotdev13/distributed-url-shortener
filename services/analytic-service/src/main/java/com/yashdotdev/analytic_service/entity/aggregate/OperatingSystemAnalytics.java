package com.yashdotdev.analytic_service.entity.aggregate;

import com.yashdotdev.analytic_service.enums.OperatingSystem;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "operating_system_analytics",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "short_code",
                                "operating_system"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatingSystemAnalytics
        extends AbstractDimensionAnalytics {

    @Enumerated(EnumType.STRING)
    @Column(
            name = "operating_system",
            nullable = false
    )
    private OperatingSystem operatingSystem;
}