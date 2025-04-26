package com.springShoppingBot.SpringShoppingBot.order;

import com.springShoppingBot.SpringShoppingBot.GlobalController;
import com.springShoppingBot.SpringShoppingBot.guestUser.GuestUser;
import com.springShoppingBot.SpringShoppingBot.guestUser.GuestUserService;
import com.springShoppingBot.SpringShoppingBot.tempProduct.TempProduct;
import com.springShoppingBot.SpringShoppingBot.tempProduct.TempProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {

    public final OrderService orderService;
    public final TempProductService tempProductService;
    public final GuestUserService userService;
    public Order currentOrder;
    public List<TempProduct> tempProductsList = new ArrayList<TempProduct>();

    public OrderController(TempProductService tempProductService,
                           GuestUserService userService,
                           OrderService orderService) {
        this.currentOrder = new Order();
        this.tempProductService = tempProductService;
        this.orderService = orderService;
        this.userService = userService;
    }

    public List<TempProduct> getAllProducts(){
        return tempProductService.getAllProducts();
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
//        currentOrder.setListOfProducts(tempProductService.getAllProducts());
        return "shoppingList.html";
    }

    /**
     * Add a new product do repository and
     * add the new product to the Order
     * @param product
     * @return
     */
    @PostMapping(path = "/product/addNewProduct")
    public String createNewProduct (@Valid TempProduct product, BindingResult result){

        if (result.hasErrors()){
            System.out.println("Wrong format.");
        }else{
            tempProductService.createNewProduct(product);
            System.out.println("New products created: "+product.getName()+" "
                    +product.getType()+" "+product.getNumOfUnits());
            tempProductsList.add(product);
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
        tempProductService.deleteProduct(productId);
        return "redirect:/shoppingList";
    }

    @PostMapping("/{orderId}/addProduct/{productId}")
    public ResponseEntity<String> addProductToOrder(@PathVariable int orderId, @PathVariable int productId) {
        orderService.addProductToHistory(orderId, productId);
        return ResponseEntity.ok("Product added to order successfully!");
    }

    @PostMapping(path = "orderSummary")
    public String submitOrder(
            Model model,
            @RequestParam(required = false) String userEmail
            ){

        List<TempProduct> products = new ArrayList<>(tempProductsList);
        currentOrder.setListOfProducts(products);
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
//        System.out.println(currentOrder.toString());

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

        System.out.println("Temp list contains: "+products.size()+" products.");

        model.addAttribute("isLoggedIn", GlobalController.getIsLoggedIn());
        model.addAttribute("username", GlobalController.getUsername());
        model.addAttribute("currentOrder", currentOrder);
        model.addAttribute("products", products);

        // Clear List of Products
        tempProductService.clearOrder();
        tempProductsList.clear();

        System.out.println("Temp list contains: "+tempProductsList.size()+" products.");

        return "summary";
    }

}
