package com.springShoppingBot.SpringShoppingBot.security;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String email;
}
