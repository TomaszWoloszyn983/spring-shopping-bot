package com.springShoppingBot.SpringShoppingBot.OrderProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<Integer, Integer> {

//    @Query("SELECT p FROM ORDER_PRODUCT p WHERE p.orderId = ?1")
//    List<Integer> findByOrderId(Integer orderId);

    @Query("SELECT po.product.id FROM ProductOrder po WHERE po.order.idOrder = :orderId")
    List<Integer> findProductIdsByOrderId(@Param("orderId") Integer orderId);

}
