package com.example.dishdash.service;

import com.example.dishdash.repository.RepositoryUsers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceUsers {
private final RepositoryUsers repositoryUsers;
}
