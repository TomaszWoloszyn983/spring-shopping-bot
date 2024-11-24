package com.springShoppingBot.SpringShoppingBot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomUserDetailsService userDetailsService;
    private JwtAuthEntryPoint authEntryPoint;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthEntryPoint authEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(authEntryPoint) // Handle unauthorized access
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions
                )
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                    .requestMatchers("/api/auth/register").permitAll()
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/", "/home", "/shoppingList/**", "/product/**").permitAll()
                    .requestMatchers(HttpMethod.GET)
                    .authenticated()
                    .anyRequest()
                    .authenticated()
                ).httpBasic(Customizer.withDefaults());
        return http.build();
    }

//    @Bean
//    public UserDetailsService users(){
//        UserDetails admin = User.builder()
//                .username("superUser")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.builder()
//                .username("duperUser")
//                .password("password")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance(); // Use plain text passwords
        return new BCryptPasswordEncoder();
    }
}
