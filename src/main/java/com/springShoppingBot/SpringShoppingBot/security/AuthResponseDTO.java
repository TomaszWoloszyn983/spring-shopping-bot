package com.springShoppingBot.SpringShoppingBot.security;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String accessToken){
        this.accessToken = accessToken;
        System.out.println("Auth response Dto is working. Access token: "+accessToken);
    }
}
