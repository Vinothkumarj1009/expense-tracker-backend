CREATE TABLE budgets
(
    id UUID PRIMARY KEY,

    user_id UUID NOT NULL,

    category_id UUID NOT NULL,

    month INTEGER NOT NULL,

    year INTEGER NOT NULL,

    amount NUMERIC(12,2) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL,

    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_budget_user
        FOREIGN KEY (user_id)
            REFERENCES users(id),

    CONSTRAINT fk_budget_category
        FOREIGN KEY (category_id)
            REFERENCES categories(id),

    CONSTRAINT uk_budget_user_category_month_year
        UNIQUE (
            user_id,
            category_id,
            month,
            year
        )
);