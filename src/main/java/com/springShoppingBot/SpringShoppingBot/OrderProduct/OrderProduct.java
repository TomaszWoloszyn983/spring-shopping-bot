package com.springShoppingBot.SpringShoppingBot.OrderProduct;

import jakarta.persistence.*;

import java.io.Serializable;

@Table(name="order_product")
@Embeddable
public class OrderProduct implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;

    @EmbeddedId
    public ProductOrderId id;

    @Column(name="ORDERID")
    private Integer orderId;

    @Column(name = "PRODUCTID")
    private Integer productId;


    public OrderProduct() {}

    public OrderProduct(Integer orderId, Integer productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public ProductOrderId getId() {
        return id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}

