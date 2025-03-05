CREATE TABLE address
(
    address_id   UUID NOT NULL,
    contact_name VARCHAR(255),
    city         VARCHAR(255),
    state        VARCHAR(255),
    country      VARCHAR(255),
    address      VARCHAR(255),
    street       VARCHAR(255),
    zip_code     VARCHAR(255),
    CONSTRAINT pk_address PRIMARY KEY (address_id)
);

CREATE TABLE basket_item
(
    basket_id     UUID    NOT NULL,
    name          VARCHAR(255),
    category1     VARCHAR(255),
    category2     VARCHAR(255),
    price         VARCHAR(255),
    quantity      INTEGER NOT NULL,
    size          VARCHAR(255),
    color         VARCHAR(255),
    stock_size_id VARCHAR(255),
    stock_code    VARCHAR(255),
    CONSTRAINT "pk_basketıtem" PRIMARY KEY (basket_id)
);

CREATE TABLE blogs
(
    id         UUID NOT NULL,
    title      VARCHAR(255),
    slug       VARCHAR(255),
    category   VARCHAR(255),
    image      VARCHAR(255),
    content    TEXT,
    lang       VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_blogs PRIMARY KEY (id)
);

CREATE TABLE buyer
(
    buyer_id             UUID NOT NULL,
    name                 VARCHAR(255),
    surname              VARCHAR(255),
    gsm_number           VARCHAR(255),
    email                VARCHAR(255),
    ip                   VARCHAR(255),
    identity_number      VARCHAR(255),
    last_login_date      VARCHAR(255),
    registration_date    VARCHAR(255),
    registration_address VARCHAR(255),
    CONSTRAINT pk_buyer PRIMARY KEY (buyer_id)
);

CREATE TABLE category
(
    category_id UUID NOT NULL,
    name        VARCHAR(255),
    CONSTRAINT pk_category PRIMARY KEY (category_id)
);

CREATE TABLE category_sub_categories
(
    category_category_id UUID NOT NULL,
    sub_categories       VARCHAR(255)
);

CREATE TABLE color_size
(
    color_size_id UUID NOT NULL,
    color         VARCHAR(255),
    stock_code    VARCHAR(255),
    product_id    UUID,
    CONSTRAINT "pk_color_sıze" PRIMARY KEY (color_size_id)
);

CREATE TABLE color_size_images
(
    color_size_color_size_id UUID NOT NULL,
    images                   VARCHAR(255)
);

CREATE TABLE colors
(
    order_id UUID NOT NULL,
    name     VARCHAR(255),
    CONSTRAINT pk_colors PRIMARY KEY (order_id)
);

CREATE TABLE contacts
(
    contact_id   UUID NOT NULL,
    name_surname VARCHAR(255),
    email        VARCHAR(255),
    message      TEXT,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_contacts PRIMARY KEY (contact_id)
);

CREATE TABLE orders
(
    order_id            UUID NOT NULL,
    buyer_id            UUID,
    shipping_address_id UUID,
    billing_address_id  UUID,
    total_price         DECIMAL,
    payment_id          VARCHAR(255),
    status              VARCHAR(255),
    created_at          TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_orders PRIMARY KEY (order_id)
);

CREATE TABLE orders_basket_items
(
    order_order_id         UUID NOT NULL,
    basket_items_basket_id UUID NOT NULL
);

CREATE TABLE product
(
    product_id     UUID    NOT NULL,
    name           VARCHAR(255),
    slug           VARCHAR(255),
    populate       BOOLEAN NOT NULL,
    new_season     BOOLEAN NOT NULL,
    category       VARCHAR(255),
    sub_category   VARCHAR(255),
    description    TEXT,
    price          DECIMAL,
    length         VARCHAR(255),
    total_stock    INTEGER,
    purchase_price DECIMAL,
    discount_price DECIMAL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    created_at     TIMESTAMP WITHOUT TIME ZONE,
    is_active      BOOLEAN,
    CONSTRAINT pk_product PRIMARY KEY (product_id)
);

CREATE TABLE stock_size
(
    stock_size_id UUID    NOT NULL,
    size          VARCHAR(255),
    stock         INTEGER NOT NULL,
    color_size_id UUID,
    CONSTRAINT "pk_stock_sıze" PRIMARY KEY (stock_size_id)
);

CREATE TABLE user_roles
(
    user_id UUID NOT NULL,
    roles   VARCHAR(255)
);

CREATE TABLE users
(
    id              UUID NOT NULL,
    first_name      VARCHAR(255),
    last_name       VARCHAR(255),
    email           VARCHAR(255),
    hashed_password VARCHAR(255),
    address         VARCHAR(255),
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    birth_date      date,
    last_login      TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE orders_basket_items
    ADD CONSTRAINT "uc_orders_basket_ıtems_basketıtems_basket_ıd" UNIQUE (basket_items_basket_id);

ALTER TABLE orders
    ADD CONSTRAINT "uc_orders_buyer_ıd" UNIQUE (buyer_id);

ALTER TABLE orders
    ADD CONSTRAINT "uc_orders_bıllıng_address_ıd" UNIQUE (billing_address_id);

ALTER TABLE orders
    ADD CONSTRAINT "uc_orders_shıppıng_address_ıd" UNIQUE (shipping_address_id);

ALTER TABLE color_size
    ADD CONSTRAINT FK_COLOR_SIZE_ON_PRODUCT_ID FOREIGN KEY (product_id) REFERENCES product (product_id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_BILLING_ADDRESS_ID FOREIGN KEY (billing_address_id) REFERENCES address (address_id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_BUYER_ID FOREIGN KEY (buyer_id) REFERENCES buyer (buyer_id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_SHIPPING_ADDRESS_ID FOREIGN KEY (shipping_address_id) REFERENCES address (address_id);

ALTER TABLE stock_size
    ADD CONSTRAINT FK_STOCK_SIZE_ON_COLOR_SIZE_ID FOREIGN KEY (color_size_id) REFERENCES color_size (color_size_id);

ALTER TABLE category_sub_categories
    ADD CONSTRAINT "fk_category_subcategorıes_on_category" FOREIGN KEY (category_category_id) REFERENCES category (category_id);

ALTER TABLE color_size_images
    ADD CONSTRAINT "fk_colorsıze_ımages_on_color_sıze" FOREIGN KEY (color_size_color_size_id) REFERENCES color_size (color_size_id);

ALTER TABLE orders_basket_items
    ADD CONSTRAINT "fk_ordbasite_on_basket_ıtem" FOREIGN KEY (basket_items_basket_id) REFERENCES basket_item (basket_id);

ALTER TABLE orders_basket_items
    ADD CONSTRAINT fk_ordbasite_on_order FOREIGN KEY (order_order_id) REFERENCES orders (order_id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_roles_on_user FOREIGN KEY (user_id) REFERENCES users (id);