package com.springShoppingBot.SpringShoppingBot.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    public Product findProductById(int productId){
        return findProductById(productId);
    }


}
