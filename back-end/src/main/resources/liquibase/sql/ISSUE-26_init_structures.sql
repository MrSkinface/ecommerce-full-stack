--liquibase formatted sql

--changeset Mike:ISSUE-26

CREATE TABLE IF NOT EXISTS country
(
    id               BIGSERIAL PRIMARY KEY,
    code             TEXT                           NOT NULL,
    name             TEXT                           NOT NULL,
    date_created     TIMESTAMP WITHOUT TIME ZONE    DEFAULT NOW(),
    last_updated     TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS state
(
    id                  BIGSERIAL PRIMARY KEY,
    name                TEXT                            NOT NULL,
    country_id          BIGINT                          NOT NULL REFERENCES country(id),
    date_created        TIMESTAMP WITHOUT TIME ZONE     DEFAULT NOW(),
    last_updated        TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS address
(
    id               BIGSERIAL PRIMARY KEY,
    city             TEXT                           NOT NULL,
    country_id       BIGINT                         NOT NULL REFERENCES country(id),
    state_id         BIGINT                         NOT NULL REFERENCES state(id),
    street           TEXT                           DEFAULT NULL,
    zip_code         TEXT                           DEFAULT NULL,
    date_created     TIMESTAMP WITHOUT TIME ZONE    DEFAULT NOW(),
    last_updated     TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS customer
(
    id               BIGSERIAL PRIMARY KEY,
    first_name       TEXT                           NOT NULL,
    last_name        TEXT                           NOT NULL,
    email            TEXT                           DEFAULT NULL,
    date_created     TIMESTAMP WITHOUT TIME ZONE    DEFAULT NOW(),
    last_updated     TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS product_category
(
    id                  BIGSERIAL PRIMARY KEY,
    category_name       TEXT                            NOT NULL,
    date_created        TIMESTAMP WITHOUT TIME ZONE     DEFAULT NOW(),
    last_updated        TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS product
(
    id                  BIGSERIAL PRIMARY KEY,
    sku                 TEXT                            NOT NULL,
    name                TEXT                            NOT NULL,
    description         TEXT                            NOT NULL,
    unit_price          NUMERIC(19, 2)                  NOT NULL,
    image_url           TEXT                            DEFAULT NULL,
    active              BOOLEAN                         NOT NULL DEFAULT FALSE,
    units_in_stock      INTEGER                         NOT NULL DEFAULT 0,
    category_id         BIGINT                          NOT NULL REFERENCES product_category(id),
    date_created        TIMESTAMP WITHOUT TIME ZONE     DEFAULT NOW(),
    last_updated        TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS orders
(
    id                      BIGSERIAL PRIMARY KEY,
    order_tracking_number   TEXT                            DEFAULT NULL,
    total_price             NUMERIC(19, 2)                  NOT NULL DEFAULT 0,
    total_quantity          INTEGER                         NOT NULL DEFAULT 0,
    billing_address_id      BIGINT                          NOT NULL REFERENCES address(id),
    customer_id             BIGINT                          NOT NULL REFERENCES customer(id),
    shipping_address_id     BIGINT                          NOT NULL REFERENCES address(id),
    status                  TEXT                            NOT NULL,
    date_created            TIMESTAMP WITHOUT TIME ZONE     DEFAULT NOW(),
    last_updated            TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS order_item
(
    id               BIGSERIAL PRIMARY KEY,
    quantity         INTEGER                        NOT NULL,
    order_id         BIGINT                         NOT NULL REFERENCES orders(id),
    product_id       BIGINT                         NOT NULL REFERENCES product(id),
    date_created     TIMESTAMP WITHOUT TIME ZONE    DEFAULT NOW(),
    last_updated     TIMESTAMP WITHOUT TIME ZONE
);