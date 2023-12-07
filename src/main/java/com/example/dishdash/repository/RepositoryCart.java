package com.example.dishdash.repository;

import com.example.dishdash.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RepositoryCart extends JpaRepository<Cart,Long> {
    @Query("select c from Cart c where c.user.id = ?1 and  c.food.id = ?2")
    Optional<Cart> findByUserAndFood(Long userId, Long foodId);
    @Query("select c from Cart  c where c.user.id = ?1")
    Optional<List<Cart>> findAllByUser(Long id);
}
