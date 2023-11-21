package com.example.dishdash.service;

import com.example.dishdash.entity.Users;
import com.example.dishdash.repository.RepositoryUsers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceUsers {
private final RepositoryUsers repositoryUsers;

    public Users findByEmail(String username) {
        return repositoryUsers.findByEmail(username).orElseThrow();
    }
}