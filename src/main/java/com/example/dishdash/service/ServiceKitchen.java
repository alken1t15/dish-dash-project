package com.example.dishdash.service;

import com.example.dishdash.entity.Kitchen;
import com.example.dishdash.repository.RepositoryKitchen;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceKitchen {
    private final RepositoryKitchen repositoryKitchen;

    public List<Kitchen> findAll() {
        return repositoryKitchen.findAll();
    }

    public Kitchen findByName(String name){
        return repositoryKitchen.findByName(name);
    }
}
