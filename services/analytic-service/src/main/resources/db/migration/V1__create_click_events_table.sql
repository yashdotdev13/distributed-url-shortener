CREATE TABLE click_events
(
    id BIGSERIAL PRIMARY KEY,

    short_code VARCHAR(12) NOT NULL,

    original_url TEXT NOT NULL,

    user_id BIGINT NOT NULL,

    clicked_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_click_events_short_code
    ON click_events(short_code);

CREATE INDEX idx_click_events_clicked_at
    ON click_events(clicked_at);

CREATE INDEX idx_click_events_user_id
    ON click_events(user_id);