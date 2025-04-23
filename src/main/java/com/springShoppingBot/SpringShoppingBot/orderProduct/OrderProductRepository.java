package com.springShoppingBot.SpringShoppingBot.orderProduct;

import com.springShoppingBot.SpringShoppingBot.productInOrder.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

    @Query("SELECT p FROM OrderProduct p WHERE p.orderId = ?1")
    List<ProductInOrder> findByOrderId(Integer orderId);

    @Query("SELECT po.product.id FROM OrderProduct po WHERE po.order.id = :orderId")
    List<OrderProduct> findProductIdsByOrderId(@Param("orderId") int orderId);

}
