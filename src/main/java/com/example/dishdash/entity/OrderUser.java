package com.example.dishdash.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_user")
@Getter
@Setter
@NoArgsConstructor
public class OrderUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long phone;

    private String status;

    private String address;

    public OrderUser(Long phone, String status, String address) {
        this.phone = phone;
        this.status = status;
        this.address = address;
    }
}