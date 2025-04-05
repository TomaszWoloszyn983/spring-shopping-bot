package com.springShoppingBot.SpringShoppingBot;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;


@Controller
public class GlobalController {

    private static Boolean isLoggedIn = false;
    private static String username = "Guest";

    public static Boolean getIsLoggedIn(){
        return isLoggedIn;
    }

    public static void setIsLoggedIn(boolean value){
        isLoggedIn = value;
    }

    public static void updateIsLoggedIn(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        isLoggedIn = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
        username = authentication.getName();
        var role = authentication.getDetails();
//        System.out.println("User: "+username+" is logged in: "+isLoggedIn+". Details: "+role);
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        GlobalController.username = username;
    }

}
