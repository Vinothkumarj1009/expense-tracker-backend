ALTER TABLE expenses
ADD COLUMN category_id UUID;

ALTER TABLE expenses
ADD CONSTRAINT fk_expenses_category
FOREIGN KEY (category_id)
REFERENCES categories(id);