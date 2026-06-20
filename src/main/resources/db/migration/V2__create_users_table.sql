CREATE TABLE users
(
    id UUID PRIMARY KEY,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100) NOT NULL,

    email VARCHAR(255) NOT NULL,

    password VARCHAR(255) NOT NULL,

    role VARCHAR(20) NOT NULL,

    status VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP,

    created_by VARCHAR(255),

    updated_by VARCHAR(255)
);

ALTER TABLE users
ADD CONSTRAINT uk_users_email
UNIQUE (email);

CREATE INDEX idx_users_email
ON users(email);

CREATE INDEX idx_users_status
ON users(status);

CREATE INDEX idx_users_role
ON users(role);