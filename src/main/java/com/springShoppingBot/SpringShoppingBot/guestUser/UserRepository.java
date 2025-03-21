package com.springShoppingBot.SpringShoppingBot.guestUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<GuestUser, Integer> {

    Optional<GuestUser> findByUsername(String Username);

    boolean existsByUsername(String Username);

//    Optional<GuestUser> findUserById(int id);
}
