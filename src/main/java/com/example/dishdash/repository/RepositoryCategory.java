package com.example.dishdash.repository;

import com.example.dishdash.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCategory extends JpaRepository<Category,Long> {
}
