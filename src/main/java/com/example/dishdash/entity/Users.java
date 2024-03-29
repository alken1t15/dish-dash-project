package com.example.dishdash.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contact;

    private Long phone;

    private String email;
    private String country;
    private String city;
    private String password;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Cart> carts;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<History> histories;

    public Users(String email) {
        this.email = email;
    }

    public Users(String contact, Long phone, String email, String country, String city, String password) {
        this.contact = contact;
        this.phone = phone;
        this.email = email;
        this.country = country;
        this.city = city;
        this.password = password;
    }
}