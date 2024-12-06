package com.senials.security.repository;

import com.senials.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}