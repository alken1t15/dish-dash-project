package com.example.dishdash.repository;

import com.example.dishdash.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryFood extends JpaRepository<Food,Long> {
//    @Query("select f from Food f where f.kitchen.name = ?1 and f.category.name = ?2")
//    List<Food> findAllByNameCategoryAndKitchen(String nameKitchen,String nameCategory);

    @Query("select f from Food f where f.category.name=?1")
    List<Food> findAllByNameCategory(String nameCategory);
}