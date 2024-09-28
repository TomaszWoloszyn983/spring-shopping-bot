package com.springShoppingBot.SpringShoppingBot.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "dt_products")
public class Product {

    @Id
    private int id;
    private String name;
    private String type;

    @Column(name = "sizeofunit")
    private String sizeOfUnit;

    @Column(name = "numofunits")
    private int numOfUnits;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSizeOfUnit() {
        return sizeOfUnit;
    }

    public void setSizeOfUnit(String sizeOfUnit) {
        this.sizeOfUnit = sizeOfUnit;
    }

    public int getNumOfUnits() {
        return numOfUnits;
    }

    public void setNumOfUnits(int numOfUnits) {
        this.numOfUnits = numOfUnits;
    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }
}
