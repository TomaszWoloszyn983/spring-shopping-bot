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
        System.out.println("\tDisplaying Home Page");

        GlobalController.updateIsLoggedIn();

        model.addAttribute("isLoggedIn", GlobalController.getIsLoggedIn());
        model.addAttribute("username", GlobalController.getUsername());

        System.out.println("User logged-in: " + GlobalController.getIsLoggedIn());

//        if (isLoggedIn == null){
//            GlobalController.setIsLoggedIn();
//            this.isLoggedIn = GlobalController.getIsLoggedIn();
//            System.out.println("Checking login status: "+isLoggedIn);
//        } else if (isLoggedIn == false) {
//            model.addAttribute("isLoggedIn", isLoggedIn);
//            System.out.println("User logged-in: "+isLoggedIn);
//        } else{
//            model.addAttribute("isLoggedIn", isLoggedIn);
//            model.addAttribute("username", GlobalController.getUsername());
//            System.out.println("User logged-in: "+isLoggedIn);
//        }


//        Authentication authentication = SecurityContextHolder
//                .getContext()
//                .getAuthentication();

        /*
            Checking if any user i logged-in and sending the login status to the front-end.
         */
//        boolean isLoggedIn = authentication != null
//                                && authentication.isAuthenticated()
//                                && !(authentication instanceof AnonymousAuthenticationToken);

//        if (isLoggedIn) {
//            model.addAttribute("username", authentication.getName());
//            model.addAttribute("isLoggedIn", true);
//            System.out.println("\tUser logged-in: "+isLoggedIn);
//        } else {
//            model.addAttribute("isLoggedIn", false);
//            System.out.println("\tUser logged-in: "+isLoggedIn);
//        }


//        model.addAttribute("isLoggedIn", isLoggedIn);
        return "index.html";
    }
}
