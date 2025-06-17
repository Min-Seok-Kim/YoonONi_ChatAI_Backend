package com.example.YoonONI_BackEnd.Entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    // 기본 생성자 필요
    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

