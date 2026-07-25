package com.yashdotdev.analytic_service.repository;

import com.yashdotdev.analytic_service.entity.aggregate.DeviceAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DeviceAnalyticsRepository
        extends JpaRepository<DeviceAnalytics, Long> {

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO device_analytics
            (short_code, device_type, clicks)
            VALUES (:shortCode, :deviceType, 1)

            ON CONFLICT (short_code, device_type)

            DO UPDATE
            SET clicks = device_analytics.clicks + 1
            """, nativeQuery = true)
    void upsert(
            @Param("shortCode") String shortCode,
            @Param("deviceType") String deviceType
    );
}