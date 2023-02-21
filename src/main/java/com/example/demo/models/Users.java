package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@ToString
public class Users {

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    @Setter @Getter
    private String userName;
    @Column(nullable = false)
    @Setter @Getter
    private String membership;

    public Users() {

    }

    public Users(String userName, String membership) {
        super();
        this.userName = userName;
        this.membership = membership;
    }
}