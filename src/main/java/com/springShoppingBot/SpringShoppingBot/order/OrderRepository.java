package com.springShoppingBot.SpringShoppingBot.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface OrderRepository  extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.id = ?1")
    Optional<Order>findOrderById(int id);
}