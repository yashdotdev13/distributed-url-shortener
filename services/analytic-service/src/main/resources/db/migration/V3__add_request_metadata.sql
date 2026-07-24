ALTER TABLE click_events
    ADD COLUMN ip_address VARCHAR(100);

ALTER TABLE click_events
    ADD COLUMN user_agent TEXT;

ALTER TABLE click_events
    ADD COLUMN referer TEXT;