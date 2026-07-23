CREATE TABLE urls
(
    id BIGSERIAL PRIMARY KEY,

    original_url TEXT NOT NULL,

    short_code VARCHAR(12) NOT NULL UNIQUE,

    user_id BIGINT NOT NULL,

    status VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    expires_at TIMESTAMP,

    last_accessed_at TIMESTAMP,

    click_count BIGINT NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX idx_short_code
    ON urls(short_code);

CREATE INDEX idx_user_id
    ON urls(user_id);

CREATE INDEX idx_status
    ON urls(status);

CREATE INDEX idx_created_at
    ON urls(created_at);