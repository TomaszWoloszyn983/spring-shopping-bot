CREATE TABLE User_Role (
    UserId INT NOT NULL,
    RoleId INT NOT NULL,
    PRIMARY KEY (UserId, RoleId),
    FOREIGN KEY (UserId) REFERENCES DT_USER(Id) ON DELETE CASCADE,
    FOREIGN KEY (RoleId) REFERENCES DT_ROLE(Id) ON DELETE CASCADE
);

CREATE TABLE Order_Product (
    OrderId INT NOT NULL,
    ProductId INT NOT NULL,
    FOREIGN KEY (OrderId) REFERENCES DT_ORDER(Id) ON DELETE CASCADE,
    FOREIGN KEY (ProductId) REFERENCES DT_PRODUCTS(Id) ON DELETE CASCADE,
    PRIMARY KEY (OrderId, ProductId)
);

CREATE TABLE User_Order (
    UserId INT NOT NULL,
    OrderId INT NOT NULL,
    FOREIGN KEY (UserId) REFERENCES DT_USER(Id) ON DELETE CASCADE,
    FOREIGN KEY (OrderId) REFERENCES DT_ORDER(Id) ON DELETE CASCADE,
    PRIMARY KEY (UserId, OrderId)
);