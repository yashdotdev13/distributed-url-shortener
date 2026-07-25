CREATE TABLE IF NOT EXISTS url_ownership
(
    short_code VARCHAR(12) PRIMARY KEY,

    owner_id BIGINT NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_url_ownership_owner
    ON url_ownership(owner_id);