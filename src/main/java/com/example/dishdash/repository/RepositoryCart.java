package com.example.dishdash.repository;

import com.example.dishdash.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RepositoryCart extends JpaRepository<Cart,Long> {
    @Query("select c from Cart c where c.user.id = ?1 and  c.food.id = ?2")
    Optional<Cart> findByUserAndFood(Long userId, Long foodId);
}
