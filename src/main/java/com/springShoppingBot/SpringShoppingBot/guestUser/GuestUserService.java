package com.springShoppingBot.SpringShoppingBot.guestUser;

import com.springShoppingBot.SpringShoppingBot.order.Order;
import com.springShoppingBot.SpringShoppingBot.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * For the moment I only need:
 *  - reading users data from repository
 *  - deleting account, but this is for later.
 *  - Admin should be able to display all GuestUsers
 */
@Service
public class GuestUserService {

    public final UserRepository userRepository;
    public final OrderRepository orderRepository;
    private GuestUser user;

    public GuestUserService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public GuestUser findUserById(int id){
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id = "+id+" not found."
                ));
    }

    public GuestUser findUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException(
                        "User = "+username+" not found."
                ));
    }

    public GuestUser findUserByEmail(String email){
//        System.out.println("Finding user email: "+email);
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalStateException(
                        "User email: "+email+" not found in the database."
                ));
    }

    public List<Order> findUsersOrders(String usersEmail){
        return orderRepository.findOrderByEmail(usersEmail);
    }


}
