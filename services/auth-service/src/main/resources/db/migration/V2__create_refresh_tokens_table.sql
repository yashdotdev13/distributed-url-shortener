CREATE TABLE refresh_tokens
(
    id BIGSERIAL PRIMARY KEY,

    token VARCHAR(512) NOT NULL UNIQUE,

    user_id BIGINT NOT NULL,

    expires_at TIMESTAMP NOT NULL,

    revoked BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP,

    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);

CREATE INDEX idx_refresh_token
    ON refresh_tokens(token);

CREATE INDEX idx_refresh_user
    ON refresh_tokens(user_id);

CREATE INDEX idx_refresh_expiry
    ON refresh_tokens(expires_at);