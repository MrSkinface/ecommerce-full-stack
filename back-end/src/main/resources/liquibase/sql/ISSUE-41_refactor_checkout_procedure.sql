--liquibase formatted sql

--changeset Mike:ISSUE-41
ALTER TABLE orders DROP COLUMN IF EXISTS billing_address_id;