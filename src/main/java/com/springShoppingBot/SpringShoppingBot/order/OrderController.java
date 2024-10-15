package com.springShoppingBot.SpringShoppingBot.order;

import com.springShoppingBot.SpringShoppingBot.product.Product;
import com.springShoppingBot.SpringShoppingBot.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {

    public final OrderService orderService;
    public final ProductService productService;
    public Order currentOrder;

    public OrderController(ProductService productService,
                           OrderService orderService) {
        currentOrder = new Order();
        this.productService = productService;
        this.orderService = orderService;
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

    @PostMapping(path = "orderSummary")
    public String submitOrder(
            Model model,
            @RequestParam(required = false) String userEmail
    ){
        System.out.println("Display summary page.");
        currentOrder.setUserEmail(userEmail);
        currentOrder.setOrderDate();
        System.out.println(currentOrder.toString());

        // Message body
//        String messageBody = "";

        String listOfProducts = currentOrder.getListOfProducts().stream()
                .map(product -> "Product Name: " + product.getName() +
                        ", Product Type: " + product.getType() +
                        ", Size: " + product.getSizeOfUnit() +
                        ", Quantity: " + product.getNumOfUnits() + "/n")
                .collect(Collectors.joining("\n"));
        String messageBody = "Your order places on: "+currentOrder.getOrderDate()+
                "/n/nFor the following products: "+"/n"+listOfProducts+
                "/nhas been sent to one of our Robots."+
                "/n/n You should receive the results very soon."+
                "/n/n Thank you and happy automation.";

        // Send email.
        orderService.sendConfirmationEmail(userEmail,
                "Order Confirmation",
                messageBody);

        model.addAttribute("currentOrder", currentOrder);
        return "summary";
    }

}
