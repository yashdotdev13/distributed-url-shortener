package com.yashdotdev.analytic_service.repository;



import com.yashdotdev.analytic_service.entity.raw.UrlAnalytics;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UrlAnalyticsRepository
        extends JpaRepository<UrlAnalytics, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO url_analytics
        (
            short_code,
            total_clicks,
            unique_clicks,
            last_clicked_at,
            created_at,
            updated_at
        )
        VALUES
        (
            :shortCode,
            1,
            0,
            :clickedAt,
            NOW(),
            NOW()
        )
        ON CONFLICT (short_code)
        DO UPDATE
        SET
            total_clicks = url_analytics.total_clicks + 1,
            last_clicked_at = EXCLUDED.last_clicked_at,
            updated_at = NOW()
        """,
            nativeQuery = true)
    void upsertAnalytics(
            @Param("shortCode") String shortCode,
            @Param("clickedAt") java.time.Instant clickedAt
    );

    Optional<UrlAnalytics> findByShortCode(String shortCode);
}