package com.springShoppingBot.SpringShoppingBot.orderProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

    /**
     * Returns Id's of Products assigned to the Order with given OrderId
     *
     * @param orderId
     * @return
     */
    @Query("SELECT po.product.id FROM OrderProduct po WHERE po.order.id = :orderId")
    List<Integer> findProductIdsByOrderId(int orderId);

}
