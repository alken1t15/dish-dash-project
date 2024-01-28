package com.example.dishdash.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "food")
@Getter
@Setter
@NoArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private String information;

    private String img;

    private Long price;

    @ManyToOne
    @JoinColumn(name = "id_category",referencedColumnName = "id")
    private Category category;

    private Integer popular;

    @OneToMany(mappedBy = "food")
    private List<Cart> carts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(name, food.name) && Objects.equals(information, food.information) && Objects.equals(price, food.price)  && Objects.equals(popular, food.popular);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, information, price, popular);
    }
}