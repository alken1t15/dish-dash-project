package com.example.dishdash.service;

import com.example.dishdash.entity.Cart;
import com.example.dishdash.entity.Food;
import com.example.dishdash.entity.History;
import com.example.dishdash.repository.RepositoryCart;
import com.example.dishdash.repository.RepositoryHistory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceHistory {
    private final RepositoryHistory repositoryHistory;
    private final EntityManager entityManager;


    public List<Food> findByPopularFoodUser() {
        Query query = entityManager.createQuery("select h from History h order by h.count desc ");
        query.setMaxResults(10);
        System.out.println(query.getResultList());
        List<History> histories = query.getResultList();
        ArrayList<Food> foods = new ArrayList<>();
        for (History history : histories){
            foods.add(history.getFood());
        }
        return foods;
    }

    public void save(History history) {
        repositoryHistory.save(history);
    }
}