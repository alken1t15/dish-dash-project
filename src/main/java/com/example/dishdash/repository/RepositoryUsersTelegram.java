package com.example.dishdash.repository;

import com.example.dishdash.entity.UsersTelegram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryUsersTelegram extends JpaRepository<UsersTelegram,Long> {
    Optional<UsersTelegram> findByIdUsers(Long idUsers);
}