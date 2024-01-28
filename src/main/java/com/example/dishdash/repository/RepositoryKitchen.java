package com.example.dishdash.repository;

import com.example.dishdash.entity.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryKitchen extends JpaRepository<Kitchen,Long> {
    Kitchen findByName(String name);
}