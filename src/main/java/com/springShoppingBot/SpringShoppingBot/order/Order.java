package com.springShoppingBot.SpringShoppingBot.order;

import com.springShoppingBot.SpringShoppingBot.product.Product;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Think if it would be possible to avoid using database for this class.
 * A good solution could be to avoid storing usersEmails.
 */


public class Order {

//    private int id;
    private String userEmail;

    private List<Product> listOfProducts = new ArrayList<Product>();

    private LocalDateTime orderDate;



    public void addToList(Product product){
        listOfProducts.add(product);
        System.out.println("Product "+product.getName()+" added to Order");
    }

    public void removeFromList(Product product){
        listOfProducts.remove(product);
        System.out.println("Product "+product.getName()+" removed from Order");
    }


//    public int getId() {
//        return id;
//    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Product> getListOfProducts() {
        System.out.println("The list contains "+listOfProducts.size()+" products");
        return listOfProducts;
    }

    public void setListOfProducts(List<Product> listOfProducts) {
        this.listOfProducts = listOfProducts;
    }

    public LocalDateTime getOrderDate() {
        return this.orderDate;
    }

    /**
     * Assign the current date and time as the variables value.
     * @param orderDate
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = LocalDateTime.now();
    }
}
