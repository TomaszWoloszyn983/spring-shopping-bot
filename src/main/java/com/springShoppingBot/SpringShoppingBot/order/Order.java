package com.springShoppingBot.SpringShoppingBot.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.springShoppingBot.SpringShoppingBot.guestUser.GuestUser;
import com.springShoppingBot.SpringShoppingBot.product.Product;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table (name = "dt_order")
@Entity(name = "Order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "useremail")
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String userEmail;



//    @Transient
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ORDER_PRODUCT", // Your existing table
            joinColumns = @JoinColumn(name = "ORDERID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCTID")
    )
    @JsonManagedReference
    private List<Product> listOfProducts = new ArrayList<Product>();

    @Column(name = "createdat")
    @DateTimeFormat(pattern = "HH:mm dd-MM-yyyy")
    private LocalDateTime createdAt;

    /**
     * This method isn't in use at the moment
     * The listOfProducts is assigned with values
     * taken from the database every time
     * the shopping list page is displayed/refreshed.
     *
     * @param product
     */
    public void addToList(Product product){
        System.out.println("Add product 1");
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
        System.out.println("Del product 1");
        listOfProducts.remove(listOfProducts.stream()
                .filter(product -> product.getId() == id)
                .findFirst());
    }

    public void clearList(){
        this.listOfProducts.clear();
    }

    public String getUserEmail() {
        return this.userEmail;
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

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Assign the current date and time as the variables value.
     * @param
     */
    public void setOrderDate() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Order{" +
                "userEmail='" + userEmail + '\'' +
                ", orderDate=" + createdAt +
                ", listOfProducts=" + listOfProducts.size() +
                '}';
    }

    public void addProduct(Product product) {
        System.out.println("Add product 2");
        this.listOfProducts.add(product);
    }

    public void removeProduct(Product product) {
        System.out.println("Del product 2");
        this.listOfProducts.remove(product);
    }


    public void displayProducts() {
        System.out.println("Product in the list:");
        this.listOfProducts.forEach(product -> System.out.print(product.getName()+" "));
        System.out.println();
    }
}
