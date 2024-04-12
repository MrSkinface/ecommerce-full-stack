--liquibase formatted sql

--changeset Mike:ISSUE-36

INSERT INTO country(code, name) VALUES
         ('BR', 'Brazil'),
         ('CA', 'Canada'),
         ('DE', 'Germany'),
         ('IN', 'India'),
         ('TR', 'Turkey'),
         ('US', 'United States');