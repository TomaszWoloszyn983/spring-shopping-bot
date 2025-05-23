DROP TABLE IF EXISTS DT_TEMP_PRODUCTS;

CREATE TABLE DT_TEMP_PRODUCTS (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(60) NOT NULL UNIQUE,
    type VARCHAR(60) DEFAULT NULL,
    sizeOfUnit VARCHAR(15) DEFAULT NULL,
    numOfUnits INT DEFAULT 1
);

CREATE SEQUENCE temp_product_sequence
START WITH 1
INCREMENT BY 1;