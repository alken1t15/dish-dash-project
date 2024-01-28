package com.example.dishdash.service;

import com.example.dishdash.entity.Cart;
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

//    public List<Food> findAllByNameCategoryAndKitchen(String nameKitchen, String nameCategory) {
//        if (nameCategory.equals("Рекомендации")){
//            Query query = entityManager.createQuery("select f from Food f where f.kitchen.name = :name order by f.popular desc ");
//            query.setParameter("name",nameKitchen);
//            query.setMaxResults(7);
//            return query.getResultList();
//        }
//        return repositoryFood.findAllByNameCategoryAndKitchen(nameKitchen, nameCategory);
//    }

    public List<Food> findAllByNameCategory(String nameCategory) {
        if (nameCategory.equals("Рекомендации")){
            System.out.println("Условие");
            Query query = entityManager.createQuery("select f from Food f order by f.popular desc ");
            query.setMaxResults(10);
            System.out.println(query.getResultList());
            return query.getResultList();
        }
        return repositoryFood.findAllByNameCategory(nameCategory);
    }

    public Food findById(Long id) {
        return repositoryFood.findById(id).orElseThrow();
    }

//    public List<Food> findAllByNameCategoryAndKitchenCustom(String nameCategory,String nameKitchen) {
//            Query query = entityManager.createQuery("select f from Food f where f.kitchen.name = ?1 order by f.popular desc ");
//            query.setParameter(1,nameKitchen);
//            return query.getResultList();
//    }
}