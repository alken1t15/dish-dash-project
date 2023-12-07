package com.example.dishdash.service;

import com.example.dishdash.entity.Users;
import com.example.dishdash.repository.RepositoryUsers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceUsers {
private final RepositoryUsers repositoryUsers;

    public Users findByEmail(String username) {
        return repositoryUsers.findByEmail(username).orElse(null);
    }

    public Users save(Users users) {
      return repositoryUsers.save(users);
    }

    public List<Users> findAll() {
        return repositoryUsers.findAll();
    }

    public void delete(Users user) {
        repositoryUsers.delete(user);
    }

    public Users findByPhone(Long phone){
        return repositoryUsers.findByPhone(phone).orElse(null);
    }
}