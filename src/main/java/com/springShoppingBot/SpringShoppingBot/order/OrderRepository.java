package com.springShoppingBot.SpringShoppingBot.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.id = ?1")
    Optional<Order>findOrderById(int id);

    @Query("SELECT o FROM Order o WHERE o.userEmail = ?1")
    List<Order>findOrderByEmail(String email);

//    @Query("INSERT INTO dt_product (name, type, sizeofunit, numofunits)")
//    List<Product> addProductToOrder(List<Product> products);

}