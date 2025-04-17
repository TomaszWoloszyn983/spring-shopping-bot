package com.springShoppingBot.SpringShoppingBot.productInOrder;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductInOrderService {

    public final ProductInOrderRepository productInOrderRepository;

    public ProductInOrderService(ProductInOrderRepository productInOrderRepository) {
        this.productInOrderRepository = productInOrderRepository;
    }


}
