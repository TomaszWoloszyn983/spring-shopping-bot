package com.springShoppingBot.SpringShoppingBot.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JWTGenerator {

    public String generatorToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityContent.JWT_EXPIRATION);

        String token = builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SecurityContent.JWT_SECRET)
                .compact();
        return token;
    }

    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SecurityContent.JWT_SECRET.getBytes()) // Use the secret key for verification
                .build()
                .parseClaimsJws(token) // Parse the full JWT
                .getBody(); // Extract the claims
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SecurityContent.JWT_SECRET).parseClaimsJws(token);
            return true;
        }catch(Exception ex){
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
