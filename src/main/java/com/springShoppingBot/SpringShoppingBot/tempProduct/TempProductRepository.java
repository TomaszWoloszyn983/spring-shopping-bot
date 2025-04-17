package com.springShoppingBot.SpringShoppingBot.tempProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TempProductRepository extends JpaRepository<TempProduct, Integer> {

    @Query("SELECT p FROM TempProduct p WHERE p.id = ?1")
    Optional<TempProduct> findProductById(int id);


}
