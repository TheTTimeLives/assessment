CREATE TABLE IF NOT EXISTS order_item (
    id SERIAL PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    price NUMERIC(19, 2),
    FOREIGN KEY (order_id) REFERENCES "order" (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);
