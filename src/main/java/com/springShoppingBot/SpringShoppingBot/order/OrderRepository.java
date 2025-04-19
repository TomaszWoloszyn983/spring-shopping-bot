package com.springShoppingBot.SpringShoppingBot.order;

import com.springShoppingBot.SpringShoppingBot.guestUser.GuestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.id = ?1")
    Optional<Order>findOrderById(int id);

    @Query("SELECT o FROM Order o WHERE o.userEmail = ?1")
    List<Order>findOrderByEmail(String email);

    @Query("SELECT o FROM Order o JOIN FETCH o.listOfProducts WHERE o.userEmail = ?1")
    List<Order> findAllByUserWithProducts(GuestUser user);

    @Query("SELECT DISTINCT o FROM Order o " +
            "JOIN FETCH o.listOfProducts " +
            "WHERE o.userEmail = :email")
    List<Order> findAllByUserEmailWithProducts(@Param("email") String email);

//    @Query("INSERT INTO dt_product (name, type, sizeofunit, numofunits)")
//    List<Product> addProductToOrder(List<Product> products);

}