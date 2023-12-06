package com.example.dishdash.service;

import com.example.dishdash.entity.Cart;
import com.example.dishdash.repository.RepositoryCart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceCart {
    private final RepositoryCart repositoryCart;

    public Cart findByUserAndFood(Long userId,Long foodId) {
        return repositoryCart.findByUserAndFood(userId, foodId).orElse(null);
    }

    public void save(Cart cart) {
        repositoryCart.save(cart);
    }

    public void delete(Cart cart){
        repositoryCart.delete(cart);
    }
}