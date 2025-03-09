package com.springShoppingBot.SpringShoppingBot.security;

public class SecurityContent {

    /*
       Expiration time will be:
       1 millisecond * 1000 * 60 seconds
        * 60 minutes * 10 hours = 10h.
    */
    public static final long JWT_EXPIRATION = 24 * 60 * 60 * 1000;
    public static final String JWT_SECRET = "Bto-HGNKAX-e8J6f8DN1rRBHZJYnyVBIl5RmjHGLHa-4UB1nJbmvXLOK7sHZNWc-ANppYznLRSn-5IblVuCtl_7YXM54XvFGi58tXUEHB2uBWyKXLxvtw3q0PkOCDYDvuCmhatN77KKCn08RTx2_kVoLEaVuEtKt8EpEwhU_YsautFxBOAxfzr4ZS7lf2RMg5hy98_AYJB2fuTsZ4l42VbmFoOBPUFeWK_7c2_P8LcD3Vs0-inYO9-yNDN0Cdj9wq6C2Ksg0MGh7n5u8hKBcMueTlg3DlvXqiFUXzrJLsY_8DW4JtEenTQle8g6JO4pYV35xWa-bPfCi9GQ-hKtcUw";
}
