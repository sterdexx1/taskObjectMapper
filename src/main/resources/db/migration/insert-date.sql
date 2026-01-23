INSERT INTO customer (id, name, surname, email, number) VALUES
(1, 'Ivan', 'Ivanov', 'ivan.ivanov@example.com', '+79991234567'),
(2, 'Maria', 'Petrova', 'maria.petrova@example.com', '+79997654321'),
(3, 'Alexey', 'Sidorov', 'alexey.sidorov@example.com', '+79991112233');
INSERT INTO order_table (id, address, cost, status, order_data, customer_id) VALUES
(1, 'Moscow, st. Pushkina, 10', 27598.99, 'OK', '2025-03-15 10:30:00', 1),
(2, 'Moscow, st. Pushkina, 11', 29989.00, 'WAIT', '2025-03-16 14:45:00', 2),
(3, 'Moscow, st. Pushkina, 12', 2999.95, 'OK', '2025-03-17 09:15:00', 3);
INSERT INTO product (id, name, description, price, amount, order_id) VALUES
(1, 'Notebook Lenovo IdeaPad', '15.6", 8GB RAM, 256GB SSD', 24999.99, 1, 1),
(2, 'Mouse Logitech G102', 'Gameing, 8000 DPI', 1299.50, 2, 1),
(3, 'Qwerty Razer BlackWidow', 'Mechanikal, RGB', 4999.00, 1, 2),
(4, 'Mouse Sony WH-1000XM4', 'Gameing, 3000 DPI', 24990.00, 1, 2),
(5, 'Flesh-memory 64GB', 'USB 3.0', 599.99, 5, 3);
