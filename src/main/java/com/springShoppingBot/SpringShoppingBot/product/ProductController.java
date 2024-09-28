package com.springShoppingBot.SpringShoppingBot.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ProductController {

    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(("shoppingList"))
    @GetMapping
    public String displayShoppingList(){
        return "shoppingList.html";
    }

    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    /**
     * Passes the list of products to the shoppingList.html template
     *
     * @param model
     * @return
     */
    @GetMapping(path="shoppingList")
    public String displayAllProducts(Model model){
        model.addAttribute("products", getAllProducts());
        return "shoppingList.html";
    }
}
