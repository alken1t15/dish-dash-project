package com.example.dishdash.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "history")
@Getter
@Setter
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_users", referencedColumnName = "id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "id_food",referencedColumnName = "id")
    private Food food;

    private Integer count;

    public History(Users user, Food food, Integer count) {
        this.user = user;
        this.food = food;
        this.count = count;
    }


}