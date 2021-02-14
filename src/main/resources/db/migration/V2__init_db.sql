SET search_path TO crud;

CREATE SEQUENCE my_seq_gen START 1;

CREATE TABLE IF NOT EXISTS customers(
    customer_id serial PRIMARY KEY,
    name VARCHAR(24) NOT NULL,
    surname VARCHAR(32) NOT NULL,
    age smallint
);

CREATE TABLE IF NOT EXISTS accounts(
   id serial PRIMARY KEY,
   name VARCHAR(32) NOT NULL,
   customer_id bigint,
   status VARCHAR(16) DEFAULT 'ACTIVE',
   FOREIGN KEY(customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE IF NOT EXISTS specialities(
    speciality_id serial PRIMARY KEY,
    spec_name VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS customer_specialities(
    customer_id bigint,
    speciality_id bigint,
    PRIMARY KEY(customer_id,speciality_id),
    FOREIGN KEY(customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY(speciality_id) REFERENCES specialities(speciality_id)
);
