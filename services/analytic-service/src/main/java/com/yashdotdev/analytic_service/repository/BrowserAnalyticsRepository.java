package com.yashdotdev.analytic_service.repository;

import com.yashdotdev.analytic_service.entity.aggregate.BrowserAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BrowserAnalyticsRepository
        extends JpaRepository<BrowserAnalytics, Long> {

    @Modifying
    @Transactional
    @Query(
            value = """
                    INSERT INTO browser_analytics
                    (short_code, browser, clicks)
                    VALUES (:shortCode, :browser, 1)

                    ON CONFLICT (short_code, browser)

                    DO UPDATE

                    SET clicks = browser_analytics.clicks + 1
                    """,
            nativeQuery = true
    )
    void upsert(

            @Param("shortCode") String shortCode,
            @Param("browser") String browser

    );

    List<BrowserAnalytics> findByShortCodeOrderByClicksDesc(String shortCode);
}
