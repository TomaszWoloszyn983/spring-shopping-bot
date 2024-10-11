package com.springShoppingBot.SpringShoppingBot.order;

import com.springShoppingBot.SpringShoppingBot.product.Product;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Think if it would be possible to avoid using database for this class.
 * A good solution could be to avoid storing usersEmails.
 */

@Controller
public class Order {

//    private int id;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String userEmail;

    private List<Product> listOfProducts = new ArrayList<Product>();

    private LocalDateTime orderDate;


    /**
     * This method isn't in use at the moment
     * The listOfProducts is assigned with values
     * taken from the database every time
     * the shopping list page is displayed/refreshed.
     *
     * @param product
     */
    public void addToList(Product product){
        listOfProducts.add(product);
        System.out.println("Product "+product.getName()+" added to Order");
    }

    /**
     * This method isn't in use at the moment
     * The listOfProducts is assigned with values
     * taken from the database every time
     * the shopping list page is displayed/refreshed.
     *
     * @param id
     */
    public void removeFromList(int id){
        listOfProducts.remove(listOfProducts.stream()
                .filter(product -> product.getId() == id)
                .findFirst());
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

    @Override
    public String toString() {
        return "Order{" +
                "userEmail='" + userEmail + '\'' +
                ", orderDate=" + orderDate +
                ", listOfProducts=" + listOfProducts +
                '}';
    }

    public void displayProducts() {
        System.out.println("Product in the list:");
        this.listOfProducts.forEach(product -> System.out.print(product.getName()+" "));
    }
}
