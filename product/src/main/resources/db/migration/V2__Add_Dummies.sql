INSERT INTO product (price, name)
VALUES (5000, '티셔츠'),
       (10000, '스웨터'),
       (15000, '바지'),
       (20000, '자켓');
INSERT INTO product_item (option_key, stock, product_id, option_value)
VALUES ('COLOR', 10, 1, '빨강'),
       ('SIZE', 10, 1, 'M'),
       ('COLOR', 5, 2, '파랑'),
       ('SIZE', 5, 2, 'L'),
       ('COLOR', 8, 3, '검정'),
       ('SIZE', 8, 3, 'S'),
       ('COLOR', 2, 4, '녹색'),
       ('SIZE', 2, 4, 'XL');
