ALTER TABLE click_events
    ADD COLUMN browser VARCHAR(50);

ALTER TABLE click_events
    ADD COLUMN operating_system VARCHAR(50);

ALTER TABLE click_events
    ADD COLUMN device_type VARCHAR(30);