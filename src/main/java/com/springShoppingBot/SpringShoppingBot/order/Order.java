package com.springShoppingBot.SpringShoppingBot.order;

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

//    @ManyToMany
    @Transient
    private List<Product> listOfProducts = new ArrayList<Product>();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private GuestUser user;

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

    public void clearList(){
        this.listOfProducts.clear();
    }


//    public int getId() {
//        return id;
//    }

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

    public String listOfProductsToString(){

        return "";
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

    public GuestUser getUser() {
        return user;
    }

    public void setUser(GuestUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userEmail='" + userEmail + '\'' +
                ", orderDate=" + createdAt +
                ", listOfProducts=" + listOfProducts.size() +
                '}';
    }

    public void displayProducts() {
        System.out.println("Product in the list:");
        this.listOfProducts.forEach(product -> System.out.print(product.getName()+" "));
        System.out.println();
    }
}
