package com.springShoppingBot.SpringShoppingBot.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.springShoppingBot.SpringShoppingBot.productInOrder.ProductInOrder;
//import com.springShoppingBot.SpringShoppingBot.tempProduct.TempProduct;
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
    private List<ProductInOrder> listOfProducts = new ArrayList<ProductInOrder>();

//    @JsonManagedReference
//    private List<TempProduct> listOfTempProducts = new ArrayList<TempProduct>();

    @Column(name = "createdat")
    @DateTimeFormat(pattern = "HH:mm dd-MM-yyyy")
    private LocalDateTime createdAt;

    /**
     * This method isn't in use at the moment
     * The listOfProducts is assigned with values
     * taken from the database every time
     * the shopping list page is displayed/refreshed.
     *
     * @param tempProduct
     */
    public void addToList(ProductInOrder tempProduct){
        System.out.println("Add product 1");
        listOfProducts.add(tempProduct);
        System.out.println("Product "+tempProduct.getName()+" added to Order");
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

    public List<ProductInOrder> getListOfProducts() {
        System.out.println("The list contains "+listOfProducts.size()+" products");
        return listOfProducts;
    }

//    public List<TempProduct> getListOfTempProducts() {
//        return listOfTempProducts;
//    }
//
//
//    public void setListOfProducts(List<TempProduct> listOfTempProducts) {
//        this.listOfTempProducts = listOfTempProducts;
//    }

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

    public void addProduct(ProductInOrder product) {
        System.out.println("Add product 2");
        this.listOfProducts.add(product);
    }

    public void removeProduct(ProductInOrder product) {
        System.out.println("Del product 2");
        this.listOfProducts.remove(product);
    }


    public void displayProducts() {
        System.out.println("Products in the list:");
        this.listOfProducts.forEach(product -> System.out.print(product.getName()+" "));
        System.out.println();
    }
}
