package com.delivery.trust_delivery_system.repository;

import com.delivery.trust_delivery_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // This allows Spring Security to find users by username during login
    Optional<User> findByUsername(String username);
}