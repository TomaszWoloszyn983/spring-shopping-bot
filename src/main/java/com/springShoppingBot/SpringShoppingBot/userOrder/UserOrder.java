package com.springShoppingBot.SpringShoppingBot.userOrder;

import jakarta.persistence.*;

@Entity
@Table(name = "USERORDER")
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iduser")
    private int userId;

    @Column(name = "idorder")
    private int orderId;


    public Long getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
