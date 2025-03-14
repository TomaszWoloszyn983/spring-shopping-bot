package com.springShoppingBot.SpringShoppingBot;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;


@Controller
public class GlobalController {

    private static Boolean isLoggedIn;
    private static String username;

    public static Boolean getIsLoggedIn(){
        return isLoggedIn;
    }

    public static void setIsLoggedIn(boolean value){
        isLoggedIn = value;
    }

    public static void setIsLoggedIn(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        isLoggedIn = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
        username = authentication.getName();
        System.out.println("User "+username+" logged in: "+isLoggedIn);
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        GlobalController.username = username;
    }

}
