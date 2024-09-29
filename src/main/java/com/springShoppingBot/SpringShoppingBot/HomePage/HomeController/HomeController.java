package com.springShoppingBot.SpringShoppingBot.HomePage.HomeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Returns index.html template
 * Displays Home Page
 */
@Controller
@RequestMapping(path = "/")
public class HomeController {

    @GetMapping({"/","home"})
    public String displayHomePage(){
        return "index.html";
    }
}
