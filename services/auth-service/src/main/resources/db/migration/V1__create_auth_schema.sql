CREATE TABLE users
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    email VARCHAR(150) NOT NULL,

    username VARCHAR(50) NOT NULL,

    password VARCHAR(255) NOT NULL,

    account_status VARCHAR(30) NOT NULL,

    provider VARCHAR(30) NOT NULL,

    email_verified BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP,

    version BIGINT NOT NULL DEFAULT 0
);

ALTER TABLE users
    ADD CONSTRAINT uk_users_email UNIQUE(email);

ALTER TABLE users
    ADD CONSTRAINT uk_users_username UNIQUE(username);

CREATE INDEX idx_users_email
    ON users(email);

CREATE INDEX idx_users_username
    ON users(username);

CREATE INDEX idx_users_provider
    ON users(provider);




CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,

    role VARCHAR(50) NOT NULL,

    PRIMARY KEY(user_id, role),

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY(user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);

CREATE INDEX idx_user_roles_user
    ON user_roles(user_id);