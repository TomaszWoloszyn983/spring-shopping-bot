package com.springShoppingBot.SpringShoppingBot.guestUser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

    @GetMapping("login")
    public String displayLoginPage(){
        return "login.html";
    }
}
