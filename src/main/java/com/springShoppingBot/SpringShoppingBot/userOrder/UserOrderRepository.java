package com.springShoppingBot.SpringShoppingBot.userOrder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
}
