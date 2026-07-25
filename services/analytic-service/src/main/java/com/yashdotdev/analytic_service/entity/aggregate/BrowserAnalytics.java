package com.yashdotdev.analytic_service.entity.aggregate;


import com.yashdotdev.analytic_service.enums.BrowserType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "browser_analytics",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "short_code",
                                "browser"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrowserAnalytics  extends AbstractDimensionAnalytics {


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BrowserType browser;
}
