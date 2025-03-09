package com.springShoppingBot.SpringShoppingBot.security;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
    private String email;

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
        System.out.println("Login Dto constructor: "+this.username+", pass: "+getPassword());
    }
}
