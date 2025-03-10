package com.springShoppingBot.SpringShoppingBot.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySources;

public class SecurityContent {

    /*
       Expiration time will be:
       1 millisecond * 1000 * 60 seconds
        * 60 minutes * 10 hours = 10h.
    */
    public static final long JWT_EXPIRATION = 24 * 60 * 60 * 1000;
    public static String JWT_SECRET = System.getenv("JWT_SECRET_KEY");
}
