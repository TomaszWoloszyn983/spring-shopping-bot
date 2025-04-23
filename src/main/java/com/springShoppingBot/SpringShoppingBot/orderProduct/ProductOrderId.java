package com.springShoppingBot.SpringShoppingBot.orderProduct;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;



@Embeddable
public class ProductOrderId implements Serializable {

        @Column(name = "orderid")
        public Integer orderId;

        @Column(name = "productid")
        public Integer productId;

        // Constructors
        public ProductOrderId() {}
        public ProductOrderId(Integer orderId, Integer productId) {
            this.orderId = orderId;
            this.productId = productId;
        }

        // Getters, Setters, equals(), and hashCode()
        // You *must* implement equals and hashCode for composite keys to work
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProductOrderId)) return false;
            ProductOrderId that = (ProductOrderId) o;
            return Objects.equals(orderId, that.orderId) &&
                    Objects.equals(productId, that.productId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, productId);
        }

        // Getters and setters
        public Integer getOrderId() { return orderId; }
        public void setOrderId(Integer orderId) { this.orderId = orderId; }

        public Integer getProductId() { return productId; }
        public void setProductId(Integer productId) { this.productId = productId; }
}


