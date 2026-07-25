package com.yashdotdev.analytic_service.repository;

import com.yashdotdev.analytic_service.entity.aggregate.OperatingSystemAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface OperatingSystemAnalyticsRepository
        extends JpaRepository<OperatingSystemAnalytics, Long> {

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO operating_system_analytics
            (short_code, operating_system, clicks)
            VALUES (:shortCode, :operatingSystem, 1)

            ON CONFLICT (short_code, operating_system)

            DO UPDATE
            SET clicks = operating_system_analytics.clicks + 1
            """, nativeQuery = true)

    void upsert(
            @Param("shortCode") String shortCode,
            @Param("operatingSystem") String operatingSystem
    );

    List<OperatingSystemAnalytics> findByShortCodeOrderByClicksDesc(String shortCode);
}