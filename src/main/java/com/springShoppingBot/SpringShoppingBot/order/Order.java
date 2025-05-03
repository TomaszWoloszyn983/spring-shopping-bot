package com.springShoppingBot.SpringShoppingBot.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.springShoppingBot.SpringShoppingBot.productInOrder.ProductInOrder;
import com.springShoppingBot.SpringShoppingBot.tempProduct.TempProduct;
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
    private Integer id;

    @Column(name = "useremail")
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String userEmail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ORDER_PRODUCT", // Your existing table
            joinColumns = @JoinColumn(name = "ORDERID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCTID")
    )
    @JsonManagedReference
    private List<ProductInOrder> listOfProducts;

    @Column(name = "createdat")
    @DateTimeFormat(pattern = "HH:mm dd-MM-yyyy")
    private LocalDateTime createdAt;

    public Integer getId() {
        return id;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<ProductInOrder> getListOfProducts() {
        return listOfProducts;
    }

    /**
     * Converts a list of TempProducts into a list of ProductsInOrder
     *
     * ... by
     * taking a list of Temporary Products.
     * Iterating the list of products, and creating a new ProductInOrder
     * for each of the TempProducts.
     *
     * @param listOfProducts - list of TempProducts
     */
    public void setListOfProducts(List<TempProduct> listOfProducts) {
        List<ProductInOrder> productsToOrder = new ArrayList<>();
        for (TempProduct product : listOfProducts){
            productsToOrder.add(new ProductInOrder(product.getId(), product.getName(),
                    product.getType(), product.getType(), product.getNumOfUnits()));
        }
        this.listOfProducts = productsToOrder;
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
