package com.example.dishdash.repository;

import com.example.dishdash.entity.OrderUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryOrderUser extends JpaRepository<OrderUser,Long> {

    Optional<List<OrderUser>> findAllByPhone(Long phone);
}
