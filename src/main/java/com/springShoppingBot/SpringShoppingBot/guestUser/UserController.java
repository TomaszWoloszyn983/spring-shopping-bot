package com.springShoppingBot.SpringShoppingBot.guestUser;

import com.springShoppingBot.SpringShoppingBot.GlobalController;
import com.springShoppingBot.SpringShoppingBot.order.Order;
import com.springShoppingBot.SpringShoppingBot.order.OrderService;
import com.springShoppingBot.SpringShoppingBot.productInOrder.ProductInOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    GuestUserService guestUserService;
    OrderService orderService;

    public UserController(GuestUserService guestUserService, OrderService orderService) {
        this.guestUserService = guestUserService;
        this.orderService = orderService;
    }

    @GetMapping("login")
    public String displayLoginPage(){
        return "login.html";
    }

    @GetMapping(path="register")
    public String displayRegisterPage(){
        System.out.println("Display register page.");
        return "register";
    }

    @GetMapping(path = "/userAccountPage")
    public String displayUserPage(Model model){
        System.out.println("Displaying User Page");

        GlobalController.updateIsLoggedIn();
        GuestUser user = guestUserService.findUserByUsername(GlobalController.getUsername());
        List<Order> userOrders = orderService.getOrdersWithProductsForUser(user.getEmail());

//      Not in use
        orderService.getProductsByOrderId(18);
//        orderService.getProductsIds(18);


        if(GlobalController.getIsLoggedIn()){
            System.out.println("User Logged-in. Email: "+user.getEmail());

            //      Retrieve the list of Products assigned to Order
            List<Order> ordersHistory = guestUserService.findUsersOrders(user.getEmail());
            System.out.println("Order History: "+ordersHistory);
            System.out.println("User Controller / list of products:");

//            Can be deleted. Display Users Orders and Orders Products
            for(Order order : ordersHistory){
                System.out.println("Date: "+order.getCreatedAt());
                for (ProductInOrder product : order.getListOfProducts())
                    System.out.println("\t\t"+product.getName());
        }

        model.addAttribute("isLoggedIn", GlobalController.getIsLoggedIn());
        model.addAttribute("username", GlobalController.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("ordersHistory", ordersHistory);
        }

        return "userAccountPage";
    }
}
