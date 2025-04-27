package com.springShoppingBot.SpringShoppingBot.productInOrder;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductInOrderService {

    public final ProductInOrderRepository productInOrderRepository;

    public ProductInOrderService(ProductInOrderRepository productInOrderRepository) {
        this.productInOrderRepository = productInOrderRepository;
    }

    public void saveProductInDatabase(ProductInOrder product){
        productInOrderRepository.save(product);
    }

    public void saveListOfProducts(List<ProductInOrder> products){
        System.out.println("Saving "+products.size()+" products in database.");
        for(ProductInOrder product : products)
            saveProductInDatabase(product);
    }


}
