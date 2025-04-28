package com.springShoppingBot.SpringShoppingBot.orderProduct;

import com.springShoppingBot.SpringShoppingBot.order.Order;
import com.springShoppingBot.SpringShoppingBot.productInOrder.ProductInOrder;
import jakarta.persistence.*;

import java.io.Serializable;

@Table(name="order_product")
@Embeddable
@Entity
public class OrderProduct implements Serializable {

    @EmbeddedId
    public ProductOrderId id;

    @Column(name="ORDERID")
    private Integer orderId;

    @Column(name = "PRODUCTID")
    private Integer productId;

    @ManyToOne
    @MapsId("orderId") // Maps to the orderId field in ProductOrderId
    @JoinColumn(name = "ORDERID")
    private Order order;

    @ManyToOne
    @MapsId("productId") // Maps to the productId field in ProductOrderId
    @JoinColumn(name = "PRODUCTID")
    private ProductInOrder product;


    public OrderProduct() {}

    public OrderProduct(Order order, ProductInOrder product) {
        this.order = order;
        this.product = product;
        this.id = new ProductOrderId(order.getId(), product.getId());
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

