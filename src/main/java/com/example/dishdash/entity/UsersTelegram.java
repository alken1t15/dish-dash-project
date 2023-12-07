package com.example.dishdash.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_telegram")
@Getter
@Setter
@NoArgsConstructor
public class UsersTelegram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_users")
    private Long idUsers;

    private Boolean status;

    public UsersTelegram(Long idUsers, Boolean status) {
        this.idUsers = idUsers;
        this.status = status;
    }
}