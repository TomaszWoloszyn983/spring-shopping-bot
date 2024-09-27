package com.springShoppingBot.SpringShoppingBot.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "dt_products")
public class Product {

    @Id
    int id;
    String name;
    String type;
    String sizeOfUnit;
    int numOfUnits;
}
