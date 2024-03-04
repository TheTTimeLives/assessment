CREATE TABLE IF NOT EXISTS "order" (
    id SERIAL PRIMARY KEY,
    quantity INT,
    price NUMERIC(19, 2)
);
