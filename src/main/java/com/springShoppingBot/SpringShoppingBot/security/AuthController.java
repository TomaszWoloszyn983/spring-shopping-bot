package com.springShoppingBot.SpringShoppingBot.security;

import com.springShoppingBot.SpringShoppingBot.guestUser.GuestUser;
import com.springShoppingBot.SpringShoppingBot.guestUser.Role;
import com.springShoppingBot.SpringShoppingBot.guestUser.RoleRepository;
import com.springShoppingBot.SpringShoppingBot.guestUser.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        if(userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        GuestUser user = new GuestUser();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());

        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

//        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
        return ResponseEntity.ok("User registered success!");
    }

    @PostMapping("/login")
    public RedirectView loginUser(@RequestParam("username") String username,
                            @RequestParam("userpassword") String userpassword){
        System.out.println("Auth Controller tries to login user: "+username);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, userpassword));
//                        loginDto.getUsername(),
//                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
//        return ResponseEntity.ok(new AuthResponseDTO(token));
        System.out.println("Login Success.");
        return new RedirectView("/userAccountPage");
    }

    @PostMapping("/logout")
    public RedirectView logout() {
        // Instruct client to discard the token
        System.out.println("You have been logged out successfully.");
//        return ResponseEntity.ok("You have been logged out successfully.");
        return new RedirectView("/home");
    }
}
