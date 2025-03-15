package com.springShoppingBot.SpringShoppingBot.security;

import com.springShoppingBot.SpringShoppingBot.guestUser.Role;
import com.springShoppingBot.SpringShoppingBot.guestUser.GuestUser;
import com.springShoppingBot.SpringShoppingBot.guestUser.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GuestUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username "+username+" not found !!!"));
        return new User(user.getUsername(),
                        user.getPassword(),
                        mapRolesToAuthorities(user.getRoles()
                        ));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
