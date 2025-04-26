package com.springShoppingBot.SpringShoppingBot.productInOrder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.springShoppingBot.SpringShoppingBot.order.Order;
import com.springShoppingBot.SpringShoppingBot.tempProduct.TempProduct;
import jakarta.persistence.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "dt_products")
public class ProductInOrder {

//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
//    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @Id
    private int id;

    private String name;
    private String type = "N/a";

    @Column(name = "sizeofunit")
    private String sizeOfUnit = "N/a";

    @Column(name = "numofunits")
    private int numOfUnits;

    @ManyToMany(mappedBy = "listOfProducts")
    @JsonBackReference
    private List<Order> orders;

    public ProductInOrder(){}

    public ProductInOrder(int id, String name, String type, String sizeOfUnit, int numOfUnits) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.sizeOfUnit = sizeOfUnit;
        this.numOfUnits = numOfUnits;
    }

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

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    public int getNumOfUnits() {
        return numOfUnits;
    }

    public void setNumOfUnits(int numOfUnits) {
        this.numOfUnits = numOfUnits;
    }

    public int getId() {
        return id;
    }

}
