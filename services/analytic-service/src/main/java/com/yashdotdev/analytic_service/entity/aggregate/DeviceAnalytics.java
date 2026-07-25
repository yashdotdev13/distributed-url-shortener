package com.yashdotdev.analytic_service.entity.aggregate;

import com.yashdotdev.analytic_service.enums.DeviceType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "device_analytics",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "short_code",
                                "device_type"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceAnalytics
        extends AbstractDimensionAnalytics {

    @Enumerated(EnumType.STRING)
    @Column(
            name = "device_type",
            nullable = false
    )
    private DeviceType deviceType;
}
