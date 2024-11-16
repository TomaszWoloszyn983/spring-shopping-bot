DROP TABLE IF EXISTS DT_ROLE;

CREATE TABLE DT_ROLE (
    Id SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL UNIQUE,
    Description VARCHAR(255)
);

INSERT INTO DT_ROLE (Name, Description) VALUES ('ADMIN', 'Allows access to application settings and to manage user accounts.');
INSERT INTO DT_ROLE (Name, Description) VALUES ('USER', 'Allows access limited to using application features');
