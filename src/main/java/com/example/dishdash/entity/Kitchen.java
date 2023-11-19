package com.example.dishdash.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "kitchen")
@Getter
@Setter
public class Kitchen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String img;

    @OneToMany(mappedBy = "kitchen")
    private List<Food> foods;
}