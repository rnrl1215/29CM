CREATE TABLE product
(
    id   BIGINT            NOT NULL auto_increment,
    name VARCHAR(255)      NOT NULL,
    price Decimal(64, 0)   NOT NULL,
    quantity INTEGER       NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE order_product
(
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    product_id  BIGINT          NOT NULL,
    order_id    BIGINT          NOT NULL,
    order_price Decimal(64, 0)  NOT NULL,
    quantity    INTEGER         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);

alter table order_product
    add constraint order_product_fk
        foreign key (product_id)
            references product (id);