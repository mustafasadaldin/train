package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name="User")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(nullable = false, unique = false)
    private String UserName;

    @Column(nullable = false, unique = false)
    private String Membership;
    public User() {
        super();
// TODO Auto-generated constructor stub
    }
    public User( String userName, String membership) {
        super();
        this.UserName = userName;
        this.Membership = membership;
    }

    public String getUserName() {
        return this.UserName;
    }
    public void setUserName(String userName) {
        this.UserName = userName;
    }
    public String getMembership() {
        return this.Membership;
    }
    public void setMembership(String membership) {
        this.Membership = membership;
    }
    @Override
    public String toString() {
        return "User [  userName=" + this.UserName +

                ", Membership=" + this.Membership + "]";
    }
}