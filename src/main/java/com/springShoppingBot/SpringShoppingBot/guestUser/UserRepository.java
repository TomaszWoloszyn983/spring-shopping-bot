package com.springShoppingBot.SpringShoppingBot.guestUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<GuestUser, Integer> {

    Optional<GuestUser> findByUsername(String Username);

    boolean existsByUsername(String Username);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<GuestUser>findUserByEmail(String email);

//    Optional<GuestUser> findUserById(int id);
}
