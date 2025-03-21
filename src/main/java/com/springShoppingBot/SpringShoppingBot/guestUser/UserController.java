package com.springShoppingBot.SpringShoppingBot.guestUser;

import com.springShoppingBot.SpringShoppingBot.GlobalController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

    GuestUserService guestUserService;

    public UserController(GuestUserService guestUserService) {
        this.guestUserService = guestUserService;
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


        model.addAttribute("isLoggedIn", GlobalController.getIsLoggedIn());
        model.addAttribute("username", GlobalController.getUsername());
        model.addAttribute("user", user);

        return "userAccountPage";
    }
}
