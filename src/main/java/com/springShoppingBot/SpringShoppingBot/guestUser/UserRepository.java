package com.springShoppingBot.SpringShoppingBot.guestUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String Username);
    boolean existsByUsername(String Username);
}
