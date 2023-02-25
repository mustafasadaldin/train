package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@ToString
@Setter @Getter
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String membership;

    public User() {

    }

    public User(String userName, String membership) {
        this.userName = userName;
        this.membership = membership;
    }
}