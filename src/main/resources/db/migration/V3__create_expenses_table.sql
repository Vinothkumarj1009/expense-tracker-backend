CREATE TABLE expenses
(
    id UUID PRIMARY KEY,

    user_id UUID NOT NULL,

    title VARCHAR(255) NOT NULL,

    description VARCHAR(1000),

    amount NUMERIC(12,2) NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_expenses_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_expenses_user_id
    ON expenses(user_id);

CREATE INDEX idx_expenses_created_at
    ON expenses(created_at);