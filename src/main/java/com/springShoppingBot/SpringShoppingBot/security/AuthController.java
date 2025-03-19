package com.springShoppingBot.SpringShoppingBot.security;

import com.springShoppingBot.SpringShoppingBot.guestUser.GuestUser;
import com.springShoppingBot.SpringShoppingBot.guestUser.Role;
import com.springShoppingBot.SpringShoppingBot.guestUser.RoleRepository;
import com.springShoppingBot.SpringShoppingBot.guestUser.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator  =jwtGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam("username") String username,
            @RequestParam("password") String userpassword,
            @RequestParam("email") String useremail,
            HttpServletResponse response
    ){
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(username);
        registerDto.setPassword(userpassword);
        registerDto.setEmail(useremail);

        if(userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        GuestUser user = new GuestUser();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());

        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        System.out.println("Creating a new "+roles.getName()+" - "+user.getUsername());
        userRepository.save(user);

        return ResponseEntity.ok("User registered success!");
    }


    /**
     * @param username
     * @param userpassword
     * @param response
     * @return
     */
    @PostMapping("/login")
    public RedirectView loginUser(
                                @RequestParam("username") String username,
                                @RequestParam("userpassword") String userpassword,
                                HttpServletResponse response){
        System.out.println("Post method received");
        try{

            LoginDto loginDto = new LoginDto(username, userpassword);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);

            Cookie jwtCookie = new Cookie("JWT-TOKEN", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true); // Set to true if using HTTPS
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60); // 1 day expiry
            response.addCookie(jwtCookie);
            System.out.println("Login Success");
            return new RedirectView("/home");
        } catch (Exception e) {
            System.out.println("Login Failure");
            return new RedirectView("/login?error=true");
        }
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest request, HttpServletResponse response) {
        // Clear Security Context
        SecurityContextHolder.clearContext();

        // Remove JWT token from cookies
        Cookie jwtCookie = new Cookie("JWT-TOKEN", "");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Set to false if not using HTTPS in dev
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Expire immediately
        response.addCookie(jwtCookie);

        System.out.println("Logout success. User logged out.");

        return new RedirectView("/home");
    }

    @GetMapping("/checkLogin")
    public String checkLoginStatus() {
        boolean isLoggedIn = isUserLoggedIn();
        if (isLoggedIn) {
            return "User is logged in";
        } else {
            return "User is not logged in";
        }
    }

    @GetMapping("/auth-error")
    public String authenticationError(Model model) {
        model.addAttribute("errorMessage", "You do not have permission to access this page.");
        return "error401"; // This refers to error.html
    }

    public boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }
}
