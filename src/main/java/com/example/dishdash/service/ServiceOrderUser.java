package com.example.dishdash.service;

import com.example.dishdash.entity.OrderUser;
import com.example.dishdash.repository.RepositoryOrderUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceOrderUser {
    private final RepositoryOrderUser repositoryOrderUser;

    public List<OrderUser> findAllByPhone(Long phone){
        return repositoryOrderUser.findAllByPhone(phone).orElse(null);
    }

    public void save(OrderUser orderUser) {
        repositoryOrderUser.save(orderUser);
    }
}
