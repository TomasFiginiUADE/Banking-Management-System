CREATE DATABASE banking_system;

USE banking_system;

CREATE TABLE accounts (

                          id INT PRIMARY KEY AUTO_INCREMENT,

                          owner_name VARCHAR(100) NOT NULL,

                          account_number VARCHAR(20) UNIQUE NOT NULL,

                          balance DECIMAL(15,2) NOT NULL
);

INSERT INTO accounts
(owner_name, account_number, balance)
VALUES

    ('Juan Perez', 'ACC1001', 250000.00),

    ('Maria Gomez', 'ACC1002', 430000.00),

    ('Carlos Diaz', 'ACC1003', 120000.00);