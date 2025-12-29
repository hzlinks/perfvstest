DROP TABLE IF EXISTS item_info;
CREATE TABLE item_info (
                           id           INTEGER PRIMARY KEY AUTOINCREMENT,
                           item_name    TEXT NOT NULL,
                           market_price INTEGER NOT NULL
);

DROP TABLE IF EXISTS item_stock;
CREATE TABLE item_stock
(
    item_id INTEGER PRIMARY KEY AUTOINCREMENT,
    stock   INTEGER NOT NULL,
    price   INTEGER NOT NULL
);


DROP TABLE IF EXISTS order_main;
CREATE TABLE order_main
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    item_id     INTEGER NOT NULL,
    item_name   TEXT NOT NULL,
    buyer_id    INTEGER NOT NULL,
    price       INTEGER                                       NOT NULL,
    item_num    INTEGER                                       NOT NULL,
    total_price INTEGER                                       NOT NULL
);


-------------------------------------
-----

INSERT INTO item_info (item_name, market_price)
VALUES ('apple1', 101);
INSERT INTO item_info (item_name, market_price)
VALUES ('apple2', 102);
INSERT INTO item_info (item_name, market_price)
VALUES ('apple3', 103);
INSERT INTO item_info (item_name, market_price)
VALUES ('apple4', 104);
INSERT INTO item_info (item_name, market_price)
VALUES ('apple5', 105);
INSERT INTO item_info (item_name, market_price)
VALUES ('apple6', 106);
INSERT INTO item_info (item_name, market_price)
VALUES ('apple7', 107);
INSERT INTO item_info (item_name, market_price)
VALUES ('apple8', 108);
INSERT INTO item_info (item_name, market_price)
VALUES ('apple9', 109);

INSERT INTO item_stock (item_id, stock, price)
VALUES (1, 1000000, 51);
INSERT INTO item_stock (item_id, stock, price)
VALUES (2, 1000000, 52);
INSERT INTO item_stock (item_id, stock, price)
VALUES (3, 1000000, 53);
INSERT INTO item_stock (item_id, stock, price)
VALUES (4, 1000000, 54);
INSERT INTO item_stock (item_id, stock, price)
VALUES (5, 1000000, 55);
INSERT INTO item_stock (item_id, stock, price)
VALUES (6, 1000000, 56);
INSERT INTO item_stock (item_id, stock, price)
VALUES (7, 1000000, 57);
INSERT INTO item_stock (item_id, stock, price)
VALUES (8, 1000000, 58);
INSERT INTO item_stock (item_id, stock, price)
VALUES (9, 1000000, 59);

