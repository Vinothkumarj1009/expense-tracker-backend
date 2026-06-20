CREATE TABLE categories
(
    id UUID PRIMARY KEY,

    name VARCHAR(100) NOT NULL,

    type VARCHAR(20) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL,

    updated_at TIMESTAMPTZ NOT NULL
);