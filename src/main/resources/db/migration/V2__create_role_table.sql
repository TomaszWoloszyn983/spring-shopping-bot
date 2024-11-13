DROP TABLE IF EXISTS Role;

CREATE TABLE Role (
    Id SERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL UNIQUE,
    Description VARCHAR(255)
);

INSERT INTO Role (Name, Description) VALUES ('ADMIN', 'Allows access to application settings and to manage user accounts.');
INSERT INTO Role (Name, Description) VALUES ('USER', 'Allows access limited to using application features');
