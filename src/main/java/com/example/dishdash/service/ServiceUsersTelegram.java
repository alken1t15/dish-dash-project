package com.example.dishdash.service;

import com.example.dishdash.entity.UsersTelegram;
import com.example.dishdash.repository.RepositoryUsersTelegram;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceUsersTelegram {
    private final RepositoryUsersTelegram repositoryUsersTelegram;

    public UsersTelegram findByIdUser(Long id){
        return repositoryUsersTelegram.findByIdUsers(id).orElse(null);
    }

    public void save(UsersTelegram user) {
        repositoryUsersTelegram.save(user);
    }
}
