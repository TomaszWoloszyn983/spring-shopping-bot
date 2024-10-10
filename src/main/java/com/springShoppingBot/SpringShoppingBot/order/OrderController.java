package com.springShoppingBot.SpringShoppingBot.order;

import com.springShoppingBot.SpringShoppingBot.product.Product;
import com.springShoppingBot.SpringShoppingBot.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {

    public final ProductService productService;
    public Order currentOrder;

    public OrderController(ProductService productService) {
        currentOrder = new Order();
        this.productService = productService;
    }

    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }



    @RequestMapping(("shoppingList"))
    @GetMapping
    public String displayShoppingList(){
        return "shoppingList.html";
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
        currentOrder.setListOfProducts(productService.getAllProducts());
        currentOrder.displayProducts();
        return "shoppingList.html";
    }

    /**
     * Add a new product do repository and
     * add the new product to the Order
     * @param product
     * @return
     */
    @PostMapping(path = "/product/addNewProduct")
    public String createNewProduct (Product product){
        System.out.println("Creating new product: "+product.getName()+" "
                +product.getType()+" "+product.getNumOfUnits());
        productService.createNewProduct(product);
        return "redirect:/shoppingList";
    }

    /**
     * Removes the product from repository by id and
     * removed the product from the current Order
     * @param productId
     * @return
     */
    @GetMapping(path = "/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable("productId") int productId){
        System.out.println("Deleting item: "+productId);
        productService.deleteProduct(productId);
        return "redirect:/shoppingList";
    }

}
