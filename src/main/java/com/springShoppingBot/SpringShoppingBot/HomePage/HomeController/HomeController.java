package com.springShoppingBot.SpringShoppingBot.HomePage.HomeController;

import com.springShoppingBot.SpringShoppingBot.GlobalController;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Returns index.html template
 * Displays Home Page
 */
@Controller
@RequestMapping(path = "")
public class HomeController {


    Boolean isLoggedIn = GlobalController.getIsLoggedIn();

    @GetMapping({"/","/home"})
    public String displayHomePage(Model model){
        GlobalController.updateIsLoggedIn();

        model.addAttribute("isLoggedIn", GlobalController.getIsLoggedIn());
        model.addAttribute("username", GlobalController.getUsername());

        System.out.println("User logged-in: " + GlobalController.getIsLoggedIn());

        return "index.html";
    }
}
