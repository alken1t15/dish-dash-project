package com.example.dishdash.repository;

import com.example.dishdash.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryUsers extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String login);

    Optional<Users> findByPhone(Long phone);
}