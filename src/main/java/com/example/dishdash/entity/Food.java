package com.example.dishdash.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "food")
@Getter
@Setter
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private String information;

    private String img;

    private Long price;

    @Column(name = "name_category")
    private String nameCategory;

    @ManyToOne
    @JoinColumn(name = "id_kitchen",referencedColumnName = "id")
    private Kitchen kitchen;

    private Integer popular;

    @OneToMany(mappedBy = "food")
    private List<Cart> carts;
}