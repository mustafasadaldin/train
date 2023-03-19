package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@ToString
@Setter
@Getter
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String level;

    public AppUser(String email, String password, String level) {
        this.email = email;
        this.password = password;
        this.level = level;
    }
}