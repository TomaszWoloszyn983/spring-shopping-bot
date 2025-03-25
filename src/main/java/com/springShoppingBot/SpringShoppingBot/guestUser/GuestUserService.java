package com.springShoppingBot.SpringShoppingBot.guestUser;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * For the moment I only need:
 *  - reading users data from repository
 *  - deleting account, but this is for later.
 *  - Admin should be able to display all GuestUsers
 */
@Service
public class GuestUserService {

    public final UserRepository userRepository;
    private GuestUser user;

    public GuestUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public GuestUser findUserById(int id){
        System.out.println("Finding product: "+id);
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id = "+id+" not found."
                ));
    }

    public GuestUser findUserByUsername(String username){
        System.out.println("Finding product: "+username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException(
                        "User = "+username+" not found."
                ));
    }

    public GuestUser findUserByEmail(String email){
        System.out.println("Finding user email: "+email);
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalStateException(
                        "User email: "+email+" not found in the database."
                ));
    }


}
