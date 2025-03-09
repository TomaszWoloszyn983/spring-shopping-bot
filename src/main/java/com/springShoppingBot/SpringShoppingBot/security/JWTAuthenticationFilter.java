package com.springShoppingBot.SpringShoppingBot.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.PrivateKey;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator tokenGenerator;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

//    @Autowired
//    public JWTAuthenticationFilter(JWTGenerator tokenGenerator,
//                                   CustomUserDetailsService customUserDetailsService) {
//        this.tokenGenerator = tokenGenerator;
//        this.customUserDetailsService = customUserDetailsService;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getJWTFromRequest(request);

        System.out.println( "\nToken received and delivered to FilterInternal: "+token);

        if(StringUtils.hasText(token) && tokenGenerator.validateToken(token)){
            String username = tokenGenerator.getUsernameFromJWT(token);
            System.out.println("Validating User: "+username);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            System.out.println("Retrived details: "+userDetails);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        System.out.println("JWT authentication filter / Validate Token: INVALID!");
        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        System.out.println("get Jwt Token from cookies storage.");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT-TOKEN".equals(cookie.getName())) {
                    System.out.println("Token retrieved from cookies: "
                        +cookie.getName()+" - "
                        +cookie.getValue());
                    return cookie.getValue();
                }
            }
        }else{
            System.out.println("Token not found in Cookies");
        }
        return null;
    }

//    private String getJWTFromRequest(HttpServletRequest request){
//        String bearerToken = request.getHeader("Authorization");
//        System.out.println("Bearer Token: "+bearerToken);
//
//        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
//            System.out.println("JWT authentication filter / get JWT from request"+bearerToken);
//            return bearerToken.substring(7, bearerToken.length());
//        }else{
//            if (request.getCookies() != null) {
//                for (Cookie cookie : request.getCookies()) {
//                    if ("JWT-TOKEN".equals(cookie.getName())) {
//                        System.out.println("JWT Authentication Filter, cookie: "+cookie.getName()+" - "+cookie.getValue());
//                        return cookie.getValue(); // Extract token from cookie
//                    }
//                }
//            }else{
//                System.out.println("JWTAuthenticationFilter class: I don't get cookie.");
//            }
//        }
//        System.out.println("JWT authentication filter / get JWT from request. NULL");
//        return null;
//    }
}
