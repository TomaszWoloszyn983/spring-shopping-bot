package com.springShoppingBot.SpringShoppingBot.tempProduct;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class TempProductController {

    public final TempProductService tempProductService;

    public TempProductController(TempProductService productService) {
        this.tempProductService = productService;
    }


    public List<TempProduct> getAllProducts(){
        return tempProductService.getAllProducts();
    }

    public TempProduct findProductById(int productId){
        return findProductById(productId);
    }


}
