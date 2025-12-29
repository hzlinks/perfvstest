DROP TABLE IF EXISTS item_info;
CREATE TABLE item_info
(
    id           INT8 GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    item_name    VARCHAR(255)                                  NOT NULL,
    market_price INTEGER                                       NOT NULL
);
COMMENT
ON TABLE item_info IS '商品表';
COMMENT
ON COLUMN item_info.item_name IS '商品名称';

DROP TABLE IF EXISTS item_stock;
CREATE TABLE item_stock
(
    item_id INT8    NOT NULL,
    stock   INTEGER NOT NULL,
    price   INTEGER NOT NULL
);
COMMENT
ON TABLE item_stock IS '商品库存表';


DROP TABLE IF EXISTS order_main;
CREATE TABLE order_main
(
    id          INT8 GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    item_id     INT8                                          NOT NULL,
    item_name   VARCHAR(255)                                  NOT NULL,
    buyer_id    INT8                                          NOT NULL,
    price       INTEGER                                       NOT NULL,
    item_num    INTEGER                                       NOT NULL,
    total_price INTEGER                                       NOT NULL
);
COMMENT
ON TABLE order_main IS '订单主表';


-----------------------------------------------------

-----------------------------------------------------

INSERT INTO public.item_info (item_name, market_price)
VALUES ('apple1', 101);
INSERT INTO public.item_info (item_name, market_price)
VALUES ('apple2', 102);
INSERT INTO public.item_info (item_name, market_price)
VALUES ('apple3', 103);
INSERT INTO public.item_info (item_name, market_price)
VALUES ('apple4', 104);
INSERT INTO public.item_info (item_name, market_price)
VALUES ('apple5', 105);
INSERT INTO public.item_info (item_name, market_price)
VALUES ('apple6', 106);
INSERT INTO public.item_info (item_name, market_price)
VALUES ('apple7', 107);
INSERT INTO public.item_info (item_name, market_price)
VALUES ('apple8', 108);
INSERT INTO public.item_info (item_name, market_price)
VALUES ('apple9', 109);

INSERT INTO public.item_stock (item_id, stock, price)
VALUES (1, 1000000, 51);
INSERT INTO public.item_stock (item_id, stock, price)
VALUES (2, 1000000, 52);
INSERT INTO public.item_stock (item_id, stock, price)
VALUES (3, 1000000, 53);
INSERT INTO public.item_stock (item_id, stock, price)
VALUES (4, 1000000, 54);
INSERT INTO public.item_stock (item_id, stock, price)
VALUES (5, 1000000, 55);
INSERT INTO public.item_stock (item_id, stock, price)
VALUES (6, 1000000, 56);
INSERT INTO public.item_stock (item_id, stock, price)
VALUES (7, 1000000, 57);
INSERT INTO public.item_stock (item_id, stock, price)
VALUES (8, 1000000, 58);
INSERT INTO public.item_stock (item_id, stock, price)
VALUES (9, 1000000, 59);

