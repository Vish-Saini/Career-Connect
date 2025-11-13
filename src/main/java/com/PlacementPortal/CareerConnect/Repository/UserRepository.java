package com.PlacementPortal.CareerConnect.Repository;

import com.PlacementPortal.CareerConnect.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // We will need this to check if an email is already registered and for logging in
    Optional<User> findByEmail(String email);
}
