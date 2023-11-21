package com.example.dishdash.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
public class Cart {
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

    @Column(name = "total_price")
    private Long totalPrice;

    public Cart(Users user, Food food, Integer count, Long totalPrice) {
        this.user = user;
        this.food = food;
        this.count = count;
        this.totalPrice = totalPrice;
    }
}