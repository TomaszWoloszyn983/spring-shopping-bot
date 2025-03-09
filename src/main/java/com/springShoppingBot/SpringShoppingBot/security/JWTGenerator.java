package com.springShoppingBot.SpringShoppingBot.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JWTGenerator {

//    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final byte[] secretKey = SecurityContent.JWT_SECRET.getBytes(StandardCharsets.UTF_8);
    private static final long EXPIRATION_TIME = SecurityContent.JWT_EXPIRATION;


    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + EXPIRATION_TIME);

        System.out.println("\nGenerate JWT Token for "+username
                +"\n\tThe token will expire on: "+expireDate
                +"\n\tSecret Key: "+getSecretKey());
        ;

        return builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
//                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .signWith(Keys.hmacShaKeyFor(getSecretKey()), SignatureAlgorithm.HS512)
                .compact();
    }


    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(getSecretKey()))
                .build()
                .parseClaimsJws(token) // Parse the full JWT
                .getBody(); // Extract the claims
        return claims.getSubject();
    }

    public boolean validateToken(String token){

        /**
         * Dodaj fragment poniżej do jakichś notatek.
         * Ciekawy sposób debugowania zgodności moich danych
         * z danymi z serwera do których nie mam swobodnego dostępu.
         * */
        try {

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(getSecretKey()))  // Ensure this is the correct key
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("Validate JWT Token."+claims);

            System.out.println("Validating Token: " + Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(getSecretKey()))// Ensure same key is used
                    .parseClaimsJws(token)
                    .getBody());

            System.out.println("Extracted Claims: " + claims);
            System.out.println("\tSubject: " + claims.getSubject());
            System.out.println("\tIssued At: " + claims.getIssuedAt());
            System.out.println("\tExpiration: " + claims.getExpiration());

            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Invalid signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Malformed token: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
        }


        try{
            Jwts.parser()
                    .setSigningKey(getSecretKey())
                    .parseClaimsJws(token);
                    /*               ^^^

                    */
            return true;
        }catch(Exception ex){
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }

    public byte[] getSecretKey() {
        return secretKey;
    }
}
