package com.testapplication.desktop.repo;

import com.testapplication.desktop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<org.springframework.security.core.userdetails.User> findByName(String username);
}
