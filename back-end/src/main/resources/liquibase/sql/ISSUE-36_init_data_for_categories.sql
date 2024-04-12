--liquibase formatted sql

--changeset Mike:ISSUE-36

INSERT INTO product_category(category_name)
VALUES ('Books'), ('Coffee Mugs'), ('Mouse Pads'), ('Luggage Tags')