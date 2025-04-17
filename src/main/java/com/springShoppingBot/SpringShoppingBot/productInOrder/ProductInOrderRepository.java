package com.springShoppingBot.SpringShoppingBot.productInOrder;

import com.springShoppingBot.SpringShoppingBot.tempProduct.TempProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Integer> {

    @Query("SELECT p FROM ProductInOrder p WHERE p.id = ?1")
    Optional<ProductInOrder> findProductById(int id);


}
