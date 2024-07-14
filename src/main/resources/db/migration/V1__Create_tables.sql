CREATE TABLE IF NOT EXISTS product (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price_in_cents INTEGER NOT NULL,
    weight_in_grams INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS purchase_order (
    id UUID PRIMARY KEY,
    order_date TIMESTAMP,
    total_amount INTEGER,
    status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS order_item (
    id UUID PRIMARY KEY,
    product_id UUID REFERENCES product(id),
    order_id UUID REFERENCES purchase_order(id),
    quantity INTEGER,
    price_in_cents INTEGER,
    total_price_in_cents INTEGER
);
