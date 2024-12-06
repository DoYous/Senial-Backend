package com.senials.security.repository;

import com.senials.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityUserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName); // 메서드 이름 수정
}
