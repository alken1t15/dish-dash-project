package com.example.dishdash.service;

import com.example.dishdash.entity.Cart;
import com.example.dishdash.repository.RepositoryCart;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public void deleteById(Long id){
        repositoryCart.deleteById(id);
    }

    @Transactional
    @Modifying
    public void delete(Cart cart){
        repositoryCart.delete(cart);
    }

    public List<Cart> findAllByUser(Long id){
        return repositoryCart.findAllByUser(id).orElse(null);
    }
}