package com.example.dishdash.service;

import com.example.dishdash.entity.Food;
import com.example.dishdash.repository.RepositoryFood;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceFood {
    private final RepositoryFood repositoryFood;
    private final EntityManager entityManager;

    public List<Food> findAllByNameCategoryAndKitchen(String nameKitchen, String nameCategory) {
        if (nameCategory.equals("Рекомендации")){
            Query query = entityManager.createQuery("select f from Food f where f.kitchen.name = :name order by f.popular desc ");
            query.setParameter("name",nameKitchen);
            query.setMaxResults(7);
            return query.getResultList();
        }
        return repositoryFood.findAllByNameCategoryAndKitchen(nameKitchen, nameCategory);
    }

    public List<Food> findAllByNameCategory(String nameCategory) {
        if (nameCategory.equals("Рекомендации")){
            Query query = entityManager.createQuery("select f from Food f order by f.popular desc ");
            query.setMaxResults(7);
            return query.getResultList();
        }
        return repositoryFood.findAllByNameCategory(nameCategory);
    }
}