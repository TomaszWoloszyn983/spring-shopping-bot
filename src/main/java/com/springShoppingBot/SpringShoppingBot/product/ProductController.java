package com.springShoppingBot.SpringShoppingBot.product;

import org.springframework.stereotype.Controller;

@Controller
public class ProductController {

    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
}
