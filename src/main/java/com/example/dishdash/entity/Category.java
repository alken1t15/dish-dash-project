package com.example.dishdash.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Food> foods;

    @ManyToOne
    @JoinColumn(name = "id_kitchen",referencedColumnName = "id")
    private Kitchen kitchen;
}