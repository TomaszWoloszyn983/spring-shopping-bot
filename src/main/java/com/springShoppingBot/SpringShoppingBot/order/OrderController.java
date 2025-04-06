package com.springShoppingBot.SpringShoppingBot.order;

import com.springShoppingBot.SpringShoppingBot.GlobalController;
import com.springShoppingBot.SpringShoppingBot.guestUser.GuestUser;
import com.springShoppingBot.SpringShoppingBot.guestUser.GuestUserService;
import com.springShoppingBot.SpringShoppingBot.guestUser.UserRepository;
import com.springShoppingBot.SpringShoppingBot.product.Product;
import com.springShoppingBot.SpringShoppingBot.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {

    public final OrderService orderService;
    public final ProductService productService;
    public final GuestUserService userService;
    public Order currentOrder;

    public OrderController(ProductService productService,
                           GuestUserService userService,
                           OrderService orderService) {
        this.currentOrder = new Order();
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
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

        System.out.println("\nUser logged-in: "+GlobalController.getIsLoggedIn());

        model.addAttribute("isLoggedIn", GlobalController.getIsLoggedIn());
        model.addAttribute("username", GlobalController.getUsername());
        model.addAttribute("products", getAllProducts());
        currentOrder.setListOfProducts(productService.getAllProducts());
        return "shoppingList.html";
    }

    /**
     * Add a new product do repository and
     * add the new product to the Order
     * @param product
     * @return
     */
    @PostMapping(path = "/product/addNewProduct")
    public String createNewProduct (@Valid Product product, BindingResult result){

        if (result.hasErrors()){
            System.out.println("Wrong format.");
        }else{
            productService.createNewProduct(product);
            System.out.println("New products created: "+product.getName()+" "
                    +product.getType()+" "+product.getNumOfUnits());
        }
        return "redirect:/shoppingList";
    }

    /**
     * Removes the product from repository by id and
     * removed the product from the current Order
     * @param productId
     * @return
     */
    @GetMapping(path = "/product/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable("productId") int productId){
        System.out.println("Deleting item: "+productId);
        productService.deleteProduct(productId);
        return "redirect:/shoppingList";
    }

    @PostMapping("/{orderId}/addProduct/{productId}")
    public ResponseEntity<String> addProductToOrder(@PathVariable int orderId, @PathVariable int productId) {
        orderService.addProductToOrder(orderId, productId);
        return ResponseEntity.ok("Product added to order successfully!");
    }

    @PostMapping(path = "orderSummary")
    public String submitOrder(
            Model model,
            @RequestParam(required = false) String userEmail
            ){

        GlobalController.updateIsLoggedIn();
        System.out.println("Display summary page.");
        if(GlobalController.getIsLoggedIn()){
            GuestUser user = userService.findUserByUsername(GlobalController.getUsername());
            System.out.println("Logged in user detected. " +
                    "\nName: "+user.getUsername() +
                    " Email: "+user.getEmail());
            currentOrder.setUserEmail(user.getEmail());
            orderService.saveOrderInUsersHistory(currentOrder);
        }else{
            currentOrder.setUserEmail(userEmail);
        }

        currentOrder.setOrderDate();
        System.out.println(currentOrder.toString());

        // Message body
        String listOfProducts = currentOrder.getListOfProducts().stream()
                .map(product -> "| " + product.getName() +
                        ", " + product.getType() +
                        ", " + product.getSizeOfUnit() +
                        ", " + product.getNumOfUnits()+" |")
                .collect(Collectors.joining("<br>"));
        String messageBody = "Your order places on: "+currentOrder.getCreatedAt()+
                "<br><br>For the following products: "+
                "<br><br>"+listOfProducts+
                "<br><br>has been sent to one of our Robots."+
                "<br> You should receive the results very soon."+
                "<br><br> Thank you and happy shopping.";

        // Send email.
        System.out.println("Sending order to: " +  ((userEmail == null) ? "Email has NOT been received !!!" : userEmail));
        orderService.sendConfirmationEmail(userEmail,
                "Order Confirmation",
                messageBody);

        // Clear List of Products
        productService.clearOrder();

        model.addAttribute("currentOrder", currentOrder);
        return "summary";
    }

}
