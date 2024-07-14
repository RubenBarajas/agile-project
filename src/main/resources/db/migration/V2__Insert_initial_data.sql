INSERT INTO product (id, name, description, price_in_cents, weight_in_grams) VALUES
('d290f1ee-6c54-4b01-90e6-d701748f0851', 'Soap', 'A gentle soap', 1000, 200),
('a783f7ee-6c54-4b01-90e6-d701748f0852', 'Soda', 'A refreshing soda', 2000, 500),
('c47a9f1e-6c54-4b01-90e6-d701748f0853', 'Water', 'Pure bottled water', 500, 1000),
('d2e4a1fe-6c54-4b01-90e6-d701748f0854', 'Shampoo', 'Hair care shampoo', 1500, 300),
('b38af7ee-6c54-4b01-90e6-d701748f0855', 'Juice', 'Natural fruit juice', 2500, 750),
('c7e9f1ee-6c54-4b01-90e6-d701748f0856', 'Milk', 'Organic milk', 1800, 1000),
('f1a4f1ee-6c54-4b01-90e6-d701748f0857', 'Bread', 'Whole grain bread', 300, 500),
('e7f9f1ee-6c54-4b01-90e6-d701748f0858', 'Butter', 'Creamy butter', 700, 250),
('d1b9f1ee-6c54-4b01-90e6-d701748f0859', 'Cheese', 'Cheddar cheese', 1200, 200),
('c4f9f1ee-6c54-4b01-90e6-d701748f0860', 'Eggs', 'Free-range eggs', 1500, 600)
ON CONFLICT (id) DO NOTHING;

INSERT INTO order_item (id, product_id, order_id, quantity, price_in_cents, total_price_in_cents) VALUES
('1d29f1ee-6c54-4b01-90e6-d701748f0861', 'd290f1ee-6c54-4b01-90e6-d701748f0851', null, 2, 1000, 2000),
('1d29f1ee-6c54-4b01-90e6-d701748f0862', 'a783f7ee-6c54-4b01-90e6-d701748f0852', null, 1, 2000, 2000),
('1d29f1ee-6c54-4b01-90e6-d701748f0863', 'c47a9f1e-6c54-4b01-90e6-d701748f0853', null, 3, 500, 1500),
('1d29f1ee-6c54-4b01-90e6-d701748f0864', 'd2e4a1fe-6c54-4b01-90e6-d701748f0854', null, 1, 1500, 1500),
('1d29f1ee-6c54-4b01-90e6-d701748f0865', 'b38af7ee-6c54-4b01-90e6-d701748f0855', null, 2, 2500, 5000),
('1d29f1ee-6c54-4b01-90e6-d701748f0866', 'c7e9f1ee-6c54-4b01-90e6-d701748f0856', null, 1, 1800, 1800)
ON CONFLICT (id) DO NOTHING;

INSERT INTO purchase_order (id, order_date, total_amount, status) VALUES
('3d29f1ee-6c54-4b01-90e6-d701748f0867', '2024-07-13T10:00:00', 2000, 'NEW'),
('3d29f1ee-6c54-4b01-90e6-d701748f0868', '2024-07-13T11:00:00', 5000, 'NEW'),
('3d29f1ee-6c54-4b01-90e6-d701748f0869', '2024-07-13T12:00:00', 1500, 'NEW')
ON CONFLICT (id) DO NOTHING;

UPDATE order_item SET order_id = '3d29f1ee-6c54-4b01-90e6-d701748f0867' WHERE id IN ('1d29f1ee-6c54-4b01-90e6-d701748f0861', '1d29f1ee-6c54-4b01-90e6-d701748f0862');
UPDATE order_item SET order_id = '3d29f1ee-6c54-4b01-90e6-d701748f0868' WHERE id IN ('1d29f1ee-6c54-4b01-90e6-d701748f0863', '1d29f1ee-6c54-4b01-90e6-d701748f0864');
UPDATE order_item SET order_id = '3d29f1ee-6c54-4b01-90e6-d701748f0869' WHERE id IN ('1d29f1ee-6c54-4b01-90e6-d701748f0865', '1d29f1ee-6c54-4b01-90e6-d701748f0866');
