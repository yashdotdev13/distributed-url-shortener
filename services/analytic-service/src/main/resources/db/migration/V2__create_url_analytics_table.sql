CREATE TABLE url_analytics
(
    id BIGSERIAL PRIMARY KEY,

    short_code VARCHAR(12) NOT NULL UNIQUE,

    total_clicks BIGINT NOT NULL DEFAULT 0,

    unique_clicks BIGINT NOT NULL DEFAULT 0,

    last_clicked_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_url_analytics_short_code
    ON url_analytics(short_code);