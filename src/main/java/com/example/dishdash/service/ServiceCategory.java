package com.example.dishdash.service;

import com.example.dishdash.repository.RepositoryCategory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceCategory {
    private final RepositoryCategory repositoryCategory;

}